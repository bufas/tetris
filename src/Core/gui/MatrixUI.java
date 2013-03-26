package Core.gui;

import Core.Engine;
import Core.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MatrixUI extends JFrame {
    private final int partialRowHeight = 5;

    private final Engine game;

    public MatrixUI(Engine e) {
        game = e;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        game.moveRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        game.moveLeft();
                        break;
                    case KeyEvent.VK_DOWN:
                        game.softDrop();
                        break;
                    case KeyEvent.VK_UP:
                        game.rotate();
                        break;
                    case KeyEvent.VK_SPACE:
                        game.hardDrop();
                        break;
                    case KeyEvent.VK_SHIFT:
                        game.hold();
                        break;
                    case KeyEvent.VK_Z:
                        game.ccRotate();
                        break;
                }
                repaint();
            }
        });

        this.setFocusTraversalKeysEnabled(false);
        this.setBackground(Color.WHITE);

        this.add(new JLabel(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int xPos, int yPos) {
                for (int col = 1; col <= Matrix.WIDTH; col++) {
                    g.setColor(getColor(game.getSlot(21, col)));

                    if (game.getSlot(21, col) != Matrix.MatrixSlot.EMPTY)
                        g.fillRect(xPos + (col-1) * 17, yPos, 15, partialRowHeight-2);
                    else
                        g.drawRect(xPos + (col-1) * 17, yPos, 15, partialRowHeight-2);
                }

                for (int row = 20; row > 0; row--) {
                    for (int col = 1; col <= Matrix.WIDTH; col++) {
                        g.setColor(getColor(game.getSlot(row, col)));

                        if (game.getSlot(row, col) != Matrix.MatrixSlot.EMPTY)
                            g.fillRect(xPos + (col-1) * 17, yPos + partialRowHeight + (20-row) * 17, 15, 15);
                        else if (game.getSlot(row, col) == Matrix.MatrixSlot.GHOST)
                            g.fillRect(xPos + (col-1) * 17, yPos + partialRowHeight + (20-row) * 17, 15, 15);
                        else
                            g.drawRect(xPos + (col-1) * 17, yPos + partialRowHeight + (20-row) * 17, 15, 15);
                    }
                }
            }

            @Override
            public int getIconWidth() {
                return 17 * Matrix.WIDTH;
            }

            @Override
            public int getIconHeight() {
                return 17 * (Matrix.HEIGHT - 2) + partialRowHeight;
            }
        }));

        this.pack();
        this.setVisible(true);
    }

    private Color getColor(Matrix.MatrixSlot slot) {
        switch (slot) {
            case I_OPEN:
            case I_LOCKED: return Color.CYAN;
            case J_OPEN:
            case J_LOCKED: return Color.BLUE;
            case L_OPEN:
            case L_LOCKED: return Color.ORANGE;
            case O_OPEN:
            case O_LOCKED: return Color.YELLOW;
            case S_OPEN:
            case S_LOCKED: return Color.GREEN;
            case Z_OPEN:
            case Z_LOCKED: return Color.RED;
            case T_OPEN:
            case T_LOCKED: return new Color(255, 0, 255);

            case GHOST:    return Color.LIGHT_GRAY;
            case EMPTY:    return Color.LIGHT_GRAY;
        }

        return null;
    }

}
