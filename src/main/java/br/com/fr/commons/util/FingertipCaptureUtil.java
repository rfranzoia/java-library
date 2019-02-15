package br.com.fr.commons.util;

import org.apache.log4j.Logger;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import br.com.fr.commons.exception.CapturerInitializationException;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class FingertipCaptureUtil {

	private static FingertipCaptureUtil instance;
	private final Logger logger = Logger.getLogger(getClass());
	private DPFPCapture capturer;
	private CapturerAction action;

	private FingertipCaptureUtil() {
		try {
			capturer = DPFPGlobal.getCaptureFactory().createCapture();
			initCapturer();
		} catch (Exception ex) {
			logger.error("CapturaDigitalUtil():constructor", ex);
		}
	}

	public static FingertipCaptureUtil getInstance() {

		if (instance == null) {
			instance = new FingertipCaptureUtil();
		}

		return instance;
	}

	public void setAction(CapturerAction action) {
		this.action = action;
	}

	public CapturerAction getAction() {
		return action;
	}

	private void initCapturer() {
		capturer.addDataListener(new DPFPDataAdapter() {
			@Override
			public void dataAcquired(final DPFPDataEvent e) {
				action.onDataAcquired(e);
			}
		});

		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override
			public void readerConnected(final DPFPReaderStatusEvent e) {
				action.onReaderConnected(e);
			}

			@Override
			public void readerDisconnected(final DPFPReaderStatusEvent e) {
				action.onReaderDisconnected(e);
			}
		});

		capturer.addSensorListener(new DPFPSensorAdapter() {
			@Override
			public void fingerTouched(final DPFPSensorEvent e) {
				action.onFingerTouched(e);
			}

			@Override
			public void fingerGone(final DPFPSensorEvent e) {
				action.onFingerGone(e);
			}
		});

		capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
			@Override
			public void onImageQuality(final DPFPImageQualityEvent e) {
				action.onImageQuality(e);
			}
		});
	}

	public void start() throws CapturerInitializationException {
		try {
			if (capturer != null) {
				if (capturer.isStarted()) {
					capturer.stopCapture();
				}

				capturer.startCapture();
			} else {
				throw new CapturerInitializationException();
			}
		} catch (Exception ex) {
			logger.error("start()", ex);
			throw new CapturerInitializationException("Erro ao iniciar dispositivo de captura!", ex);
		}
	}

	public void stop() {
		try {
			if (capturer != null) {
				capturer.stopCapture();
			}
		} catch (Exception ex) {
			logger.error("stop()", ex);
		}
	}

	public void cancel() {
		try {
			if (capturer != null && capturer.isStarted()) {
				capturer.stopCapture();
			}
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}

	public DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
}
