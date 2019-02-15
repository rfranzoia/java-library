package br.com.fr.commons.util;

import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;
import uk.co.mmscomputing.device.scanner.ScannerListener;

public class DefaultScannerListener implements ScannerListener {

    private ScannerActionAdapter actions;

    public DefaultScannerListener(ScannerActionAdapter actions) {
        this.actions = actions;
    }

    @Override
    public void update(Type type, ScannerIOMetadata siom) {

        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            if (!actions.hasFoundError() && actions != null) {
                actions.acquire(siom.getImage());
            }
        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            if (!actions.hasFoundError() && actions != null) {

                try {
                    actions.negotiate(siom.getDevice());
                } catch (Exception ex) {
                    actions.exception(ex);
                }
                
            }
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
            if (!actions.hasFoundError() && actions != null) {
                actions.stateChanged(siom);
            }
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            if (!actions.hasFoundError() && actions != null) {
                actions.exception(siom.getException());
            }
        }

    }

}
