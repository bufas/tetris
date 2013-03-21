package Core.gui;

import Core.Engine;

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
                Color[][] matrix = game.getMatrix();

                for (int x = 0; x < matrix[1].length; x++) {
                    if (matrix[1][x] != null) {
                        g.setColor(matrix[1][x]);
                        g.fillRect(xPos + x * 17, yPos, 15, partialRowHeight-2);
                    } else {
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawRect(xPos + x * 17, yPos, 15, partialRowHeight-2);
                    }
                }

                for (int row = 2; row < matrix.length; row++) {
                    for (int col = 0; col < matrix[row].length; col++) {
                        if (matrix[row][col] != null) {
                            g.setColor(matrix[row][col]);
                            g.fillRect(xPos + col * 17, yPos + partialRowHeight + (row-2) * 17, 15, 15);
                        } else {
                            g.setColor(Color.LIGHT_GRAY);
                            g.drawRect(xPos + col * 17, yPos + partialRowHeight + (row-2) * 17, 15, 15);
                        }
                    }
                }
            }

            @Override
            public int getIconWidth() {
                return 17 * Engine.WIDTH;
            }

            @Override
            public int getIconHeight() {
                return 17 * (Engine.HEIGHT - 2) + partialRowHeight;
            }
        }));

        this.pack();
        this.setVisible(true);
    }

}
