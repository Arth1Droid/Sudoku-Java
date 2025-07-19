package br.com.dio.ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class CheckGameStatus extends JButton{

    public CheckGameStatus(final ActionListener actionListener){
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }

}
