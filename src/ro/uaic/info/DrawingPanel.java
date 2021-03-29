package ro.uaic.info;

import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.LinkedList;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;

    BufferedImage image;
    BufferedImage oldImage;
    Graphics2D graphics;

    //Used for deleting shapes
    LinkedList<CreatedShape> createdShapes = new LinkedList<>();

    //Used for Free Drawing
    int xStart = -1;
    int yStart = -1;
    int xEnd;
    int yEnd;
    int xMin, yMin, xMax, yMax;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        initialization();
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE); //fill the image with white
        graphics.fillRect(0, 0, W, H);
    }

    private void initialization() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (frame.configPanel.getModeMode()) {
                    //SetMode
                    case 0 -> {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            drawShape(e.getX(), e.getY());
                            repaint();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            //Deletes Shapes
                            for (int i = 0; i < createdShapes.size(); i++) {
                                if (Math.sqrt((createdShapes.get(i).getX() - e.getX()) * (createdShapes.get(i).getX() -
                                        e.getX()) + (createdShapes.get(i).getY() - e.getY()) * (createdShapes.get(i).getY()
                                        - e.getY())) <= createdShapes.get(i).getRadius()) {
                                    eraseShape(createdShapes.get(i).getX(), createdShapes.get(i).getY(),
                                            createdShapes.get(i).getRadius(), createdShapes.get(i).getSides(),
                                            createdShapes.get(i).getType());
                                    createdShapes.remove(i);
                                    i--;
                                }
                            }
                            for (CreatedShape i : createdShapes) {
                                redrawShape(i.getX(), i.getY(), i.getRadius(), i.getSides(), i.getColor(), i.getType());
                            }
                            repaint();
                        }
                    }
                    //FreeMode
                    case 1 -> {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            xStart = e.getX();
                            xMax = xStart;
                            xMin = xStart;

                            yStart = e.getY();
                            yMax = yStart;
                            yMin = yStart;

                            oldImage = deepCopy(image);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (frame.configPanel.getModeMode() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    xEnd = e.getX();
                    yEnd = e.getY();
                    if (Math.sqrt((xStart - xEnd) * (xStart - xEnd) + (yStart - yEnd) * (yStart - yEnd)) < 5) {
                        //Center
                        //(xMax+xMin) / 2 , (yMax + yMin) / 2
                        //Left Corner
                        //(xMax+xMin) / 2 - ((xMax - xMin) + (yMax - yMin)) / 4 , (yMax + yMin) / 2 - ((xMax - xMin) + (yMax - yMin)) / 4
                        //Length
                        //((xMax - xMin) + (yMax - yMin)) / 2

                        drawRing((xMax + xMin) / 2 - ((xMax - xMin) + (yMax - yMin)) / 4,
                                (yMax + yMin) / 2 - ((xMax - xMin) + (yMax - yMin)) / 4,
                                ((xMax - xMin) + (yMax - yMin)) / 2);
                        repaint();
                    } else {
                        drawLine(xStart, yStart, xEnd, yEnd);
                        repaint();
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (frame.configPanel.getModeMode() == 1) {
                    if (e.getX() > xMax)
                        xMax = e.getX();
                    if (e.getY() > yMax)
                        yMax = e.getY();
                    if (e.getX() < xMin)
                        xMin = e.getX();
                    if (e.getY() < yMin)
                        yMin = e.getY();

                    drawComponent(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    private void drawShape(int x, int y) {
        int radius = frame.configPanel.getSizeOfShape();
        int sides = frame.configPanel.getSides();
        Color color = frame.configPanel.getColor();

        graphics.setColor(color);
        switch (frame.configPanel.getShapeMode()) {
            case 0:
                graphics.fill(new RegularPolygon(x, y, radius, sides));
                createdShapes.add(new CreatedShape(x, y, radius, sides, frame.configPanel.getColor(), 0));
                break;
            case 1:
                graphics.fill(new NodeShape(x, y, radius));
                createdShapes.add(new CreatedShape(x, y, radius, sides, frame.configPanel.getColor(), 1));
                break;
            case 2:
                graphics.fill(new Rectangle(x - radius / 2, y - radius / 2, radius, radius));
                createdShapes.add(new CreatedShape(x, y, radius, 4, frame.configPanel.getColor(), 2));
                break;
        }
    }

    private void eraseShape(int x, int y, int radius, int sides, int type) {
        graphics.setColor(Color.WHITE);
        switch (type) {
            case 0 -> graphics.fill(new RegularPolygon(x, y, radius, sides));
            case 1 -> graphics.fill(new NodeShape(x, y, radius));
            case 2 -> graphics.fill(new Rectangle(x - radius / 2, y - radius / 2, radius, radius));
        }
    }

    private void redrawShape(int x, int y, int radius, int sides, Color color, int type) {
        graphics.setColor(color);
        switch (type) {
            case 0 -> graphics.fill(new RegularPolygon(x, y, radius, sides));
            case 1 -> graphics.fill(new NodeShape(x, y, radius));
            case 2 -> graphics.fill(new Rectangle(x - radius / 2, y - radius / 2, radius, radius));
        }
    }

    private void drawRing(int x, int y, int length) {
        graphics.drawImage(oldImage, 0, 0, null);
        graphics.setColor(frame.configPanel.getColor());
        graphics.setStroke(new BasicStroke(frame.configPanel.getSizeOfShape(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        graphics.drawArc(x, y, length, length, 0, 360);
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawImage(oldImage, 0, 0, null);
        graphics.setColor(frame.configPanel.getColor());
        graphics.setStroke(new BasicStroke(frame.configPanel.getSizeOfShape(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        graphics.drawLine(x1, y1, x2, y2);
    }

    private void drawComponent(int x, int y) {
        graphics.setColor(frame.configPanel.getColor());
        graphics.fillOval(x - frame.configPanel.getSizeOfShape() / 2, y - frame.configPanel.getSizeOfShape() / 2, frame.configPanel.getSizeOfShape(), frame.configPanel.getSizeOfShape());
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
