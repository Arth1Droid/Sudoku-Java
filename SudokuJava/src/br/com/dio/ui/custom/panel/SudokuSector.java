package br.com.dio.ui.custom.panel;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import static java.awt.Color.black;

public class SudokuSector extends JPanel {
    public SudokuSector(){
        var dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder( black, 2, true));
        this.setVisible(true);
    }
}
