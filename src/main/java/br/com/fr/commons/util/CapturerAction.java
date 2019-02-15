package br.com.fr.commons.util;

import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class CapturerAction {

	public void onDataAcquired(final DPFPDataEvent e) {
	}

	public void onReaderConnected(final DPFPReaderStatusEvent e) {
	}

	public void onReaderDisconnected(final DPFPReaderStatusEvent e) {
	}

	public void onFingerTouched(final DPFPSensorEvent e) {
	}

	public void onFingerGone(final DPFPSensorEvent e) {
	}

	public void onImageQuality(final DPFPImageQualityEvent e) {
	}

}
