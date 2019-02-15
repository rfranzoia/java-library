package br.com.fr.commons.util;

import java.awt.image.BufferedImage;

import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;

public class ScannerActionAdapter {

    private boolean hasFoundError = false;

    public void acquire(BufferedImage image) {
    }

    public void negotiate(ScannerDevice device) {
    }

    public void stateChanged(ScannerIOMetadata siom) {
    }

    public void exception(Exception ex) {
    }

    public boolean hasFoundError() {
        return hasFoundError;
    }

    public void setFoundError(boolean hasFoundError) {
        this.hasFoundError = hasFoundError;
    }
}
