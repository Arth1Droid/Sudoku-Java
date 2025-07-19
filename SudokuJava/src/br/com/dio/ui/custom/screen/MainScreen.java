package br.com.dio.ui.custom.screen;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import br.com.dio.service.BoardService;
import br.com.dio.ui.custom.button.FinishGameButton;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.panel.MainPanel;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);
    
    private final BoardService boardService;
    
    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;




    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }
    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.validate();
        mainFrame.repaint();
    }
    
    private void addFinishGameButton(final JPanel mainPanel) {
    finishGameButton = new FinishGameButton(e -> {
        if (boardService.gameeIsFinished()) {
            JOptionPane.showMessageDialog(null, "Parabéns você concluiu o jogo");
            resetButton.setEnabled(false);
            checkGameStatusButton.setEnabled(false);
            finishGameButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistência, ajuste e tente novamente");
        }
    });
    mainPanel.add(finishGameButton);
}
    

    private void addCheckGameStatusButton(final JPanel mainPanel) {
      checkGameStatusButton = new JButton("Verificar Jogo");
      checkGameStatusButton.addActionListener(e -> {
        var hasErrors = boardService.hasErrors();
        var gameStatus = boardService.getStatus();
        var message = switch (gameStatus) {
            case NON_STARTED -> "O jogo não foi iniciado";
            case INCOMPLETE -> "O jogo está incompleto";
            case COMPLETE -> "O jogo está completo";
        };
        message += hasErrors ? " e contém erros" : " e não contém erros";
        JOptionPane.showMessageDialog(null, message);
    });
    mainPanel.add(checkGameStatusButton);
}

  
    private void addResetButton(final JPanel mainPanel) {
    resetButton = new ResetButton(e -> {
        var dialogResult = JOptionPane.showConfirmDialog(
            null,
            "Deseja realmente reiniciar o jogo?",
            "Limpar o jogo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (dialogResult == 0) {
            boardService.reset();
        }
    });
    mainPanel.add(resetButton);
}
    
}
