package br.com.fr.commons.util;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class HandCanvas {

    public enum Direcao {
        Esquerda, Direita
    };

    private Image image;

    private int selectedFinger;
    private Direcao direcao;

    private List<Finger> dedos;
    private final List<CirculoDedo> circulos = new ArrayList<>();

    public HandCanvas(Direcao direcao) {
        this.direcao = direcao;
        createImage(direcao);
    }

    public void setDedos(List<Finger> dedos) {
        this.dedos = dedos;
    }
    
    public void setSelectedFinger(int selectedFinger) {
        this.selectedFinger = selectedFinger;
    }
    
    public Finger getDedoAt(int index) {
        return dedos.get(index);
    }

    private void createImage(Direcao direcao) {
        this.direcao = direcao;

        switch (direcao) {
            case Esquerda:
                image = new Image(getClass().getResourceAsStream("/images/mao-1.png"));
                break;
            case Direita:
                image = new Image(getClass().getResourceAsStream("/images/mao-2.png"));
                break;
        }
    }

    public Canvas createCanvas(Consumer<Finger> mouseClickedAction) {
        Canvas canvas = createCanvas();
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            
            CanvasPoint p = new CanvasPoint(me);
            Finger dedo = getDedoByPoint(p);
            
            if (dedo != null) {
                Platform.runLater(() -> {
                    mouseClickedAction.accept(dedo);
                });
            }
            
        });
        
        return canvas;
    }
    
    public Canvas createCanvas() {
        final Canvas canvas = new Canvas((int) image.getWidth(), (int) image.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(image, 0, 0);

        if (dedos == null || dedos.isEmpty()) {
            drawNumbers(gc);
            drawMarks(gc);

        } else {
            drawNumbers(gc);
            drawColors(gc);
        }

        canvas.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent me) -> {
            Tooltip t = new Tooltip("M�o " + direcao);
            Tooltip.install(canvas, t);
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent me) -> {
            showTooltip(me, canvas);
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, (MouseEvent me) -> {
            showTooltip(me, canvas);
        });

        return canvas;
    }

    private Finger getDedoByPoint(CanvasPoint p) {
    
        for (CirculoDedo cd : circulos) {
            if (p.x >= cd.point.x && p.x <= (cd.point.x + 20) &&
                    p.y >= cd.point.y && p.y <= (cd.point.y + 20)) {
                return cd.dedo;
            }
        }
        
        return null;
    }
    
    private void showTooltip(MouseEvent me, Canvas canvas) {
        CanvasPoint p = new CanvasPoint(me);
        
        circulos.stream().forEach((CirculoDedo cd) -> {
            if (p.x >= cd.point.x && p.x <= (cd.point.x + 20) &&
                    p.y >= cd.point.y && p.y <= (cd.point.y + 20)) {
                Tooltip t = new Tooltip(cd.dedo.toString() + "\n" + getStatusTextBySituacao(cd.dedo.getSituacao()));
                Tooltip.install(canvas, t);
            }
            
        });
    }

    private void drawNumbers(GraphicsContext gc) {
        int xx = 15;
        int yy = (int) image.getHeight() / 2;

        switch (direcao) {
            case Esquerda:
                for (int d = 1; d < 6; d++) {
                    int x = xx + (30 * (d - 1));
                    if (d == 5) {
                        x -= 9;
                        yy += 50;
                    }
                    gc.strokeText(String.valueOf(d), x, yy);
                }
                break;
            case Direita:
                xx = 145;
                for (int d = 1; d < 6; d++) {
                    int x = xx - (30 * (d - 1));
                    if (d == 5) {
                        x += 9;
                        yy += 50;
                    }
                    gc.strokeText(String.valueOf(11 - d), x, yy);
                }
                break;
        }
    }

    private void drawMarks(GraphicsContext gc) {
        int w = 16;
        int h = 20;

        int x = 14;
        int y = (int) image.getHeight() / 4;

        if (selectedFinger > 0 && selectedFinger <= 5) {
            gc.setFill(Color.GRAY);

            if (selectedFinger == 5) {
                y = 121;

            } else if (selectedFinger > 1) {
                y = 35;
            }

            switch (direcao) {
                case Esquerda:
                    if (selectedFinger == 1) {
                        x--;
                    } else if (selectedFinger == 4) {
                        x++;
                    }
                    x = x + ((selectedFinger - 1) * 23);
                    gc.fillRoundRect(x, y, w, h, 90, 90);

                    break;
                case Direita:
                    x = (int) image.getWidth() / 2 - 23;
                    if (selectedFinger == 1) {
                        x++;
                    } else if (selectedFinger == 4) {
                        x--;
                    }
                    x = x - ((selectedFinger - 1) * 23);
                    gc.fillRoundRect(x, y, w, h, 90, 90);
                    break;
            }
        }
    }

    private void drawColors(GraphicsContext gc) {
        for (Finger dedo : dedos) {
            int w = 16;
            int h = 20;

            int x = 14;
            int y = (int) image.getHeight() / 4;

            Color color = getColorBySituacao(dedo.getSituacao());
            gc.setFill(color);

            switch (direcao) {
                case Esquerda:
                    if (dedo.getNumDedo() == 5) {
                        y = ((int) image.getHeight() / 3) + 56;
                        x += 7;
                    }

                    if (dedo.getNumDedo() == 1) {
                        x--;

                    } else if (dedo.getNumDedo() == 4) {
                        x++;
                    }

                    x = x + ((dedo.getNumDedo() - 1) * 28);
                    
                    gc.fillRoundRect(x, y, w, h, 90, 90);
                    
                    if (dedo.getNumDedo() == selectedFinger) {
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRoundRect(x+3, y+3, w-6, h-6, 90, 90);
                    }
                    break;
                case Direita:
                    x = 135; //((int) image.getWidth() / 2) - 29;

                    if (dedo.getNumDedo() == 6) {
                        y = ((int) image.getHeight() / 3) + 56;
                        x -= 7;
                    }

                    if (dedo.getNumDedo() == 10) {
                        x++;

                    } else if (dedo.getNumDedo() == 7) {
                        x--;
                    }

                    x = x - ((10 - dedo.getNumDedo()) * 28);
                    gc.fillRoundRect(x, y, w, h, 90, 90);
                    
                    if (dedo.getNumDedo() == selectedFinger) {
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRoundRect(x+3, y+3, w-6, h-6, 90, 90);
                    }
                    break;
            }
            
            circulos.add(new CirculoDedo(dedo, new CanvasPoint(x, y)));
        }
    }

    private String getStatusTextBySituacao(Integer status) {

        if (status == null) {
            return "N�o Capturado";

        } else if (status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_CAPTURADA)) {
            return "Capturado";

        } else if (status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_ACIDENTADO)) {
            return "Acidentado";

        } else if (status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_SEM_DEDO)) {
            return "Sem Dedo";
        }

        return "N�o Capturado";
    }
    
    private Color getColorBySituacao(Integer status) {

        if (status == null) {
            return Color.GRAY;

        } else if (status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_CAPTURADA)) {
            return Color.GREEN;

        } else if (status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_ACIDENTADO) || status.equals(BiometricsContants.ST_CAPTURA_DIGITAL_SEM_DEDO)) {
            return Color.DARKORANGE;

        }

        return Color.GRAY;

    }

    private class CirculoDedo {
        public Finger dedo;
        public CanvasPoint point;

        public CirculoDedo(Finger dedo, CanvasPoint point) {
            this.dedo = dedo;
            this.point = point;
        }
        
    }
    
}
