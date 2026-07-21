package ui;

import javax.swing.*;
import java.awt.*;

public class TechButton extends JButton {

    private final Color normalColor;
    private final Color hoverColor;

    public TechButton(
            String text,
            Color normalColor,
            Color hoverColor) {

        super(text);

        this.normalColor = normalColor;
        this.hoverColor = hoverColor;

        setForeground(Color.WHITE);
        setBackground(normalColor);

        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);

        setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        addMouseListener(

                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        setBackground(
                                TechButton.this.hoverColor
                        );

                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        setBackground(
                                TechButton.this.normalColor
                        );

                    }

                }

        );
    }
}