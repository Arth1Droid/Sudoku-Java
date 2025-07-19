package br.com.dio;
import br.com.dio.model.Board;
import br.com.dio.model.Space;
import static br.com.dio.utill.BoardTemplate.BOARD_TEMPLATE;
import java.util.Scanner;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private final static int BOARD_LIMIT = 9;
    private static Board board;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
             .collect(toMap(
                k -> k.split(";")[0],    
                v -> v.split(";")[1]      
             ));       
        var option = -1;
        while(true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar o jogo atual");
            System.out.println("5 - Verificar o status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida. selecione uma das opções no menu");
            }
        }
  }

    private static void startGame(final Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
    }
    
    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido: ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será inserido: ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        if(!board.changeValue(col, row, value)){
            System.out.printf("A posição [%s, %s] tem um valor fixo \n", col, row); 
        }

    }
    
    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido: ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será inserido: ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);

        if(!board.clearValue(col, row)){
            System.out.printf("A posição [%s, %s] tem um valor fixo \n", col, row); 
        }
    }

    private static void showCurrentGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }   
        
        var args = new Object[81];
        var argPos = 0;
        for(int i = 0; i < BOARD_LIMIT; i++){
            for(var col: board.getSpaces()){
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }   
        System.out.printf("O jogo atualmente se encontra no Status %s\n",board.getStatus().getLabel());
        if(board.hasErrors()){
            System.out.println("O jogo contém erros");
        }
        else{
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        
        System.out.println("Tem certeza que deseja limpar o jogo ? (O progresso será perdido)");
        var confirm = scanner.next();
        while(!confirm.equalsIgnoreCase("sim") || !confirm.equalsIgnoreCase("não")){
            System.out.println("Informe 'sim' ou 'não'");
             confirm = scanner.next();
        }
        if (confirm.equalsIgnoreCase( "sim")) {
            board.reset();  
        }
    }

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }   
        if (board.gameeIsFinished()) {
            System.out.println("Parabens você concluiu o jogo");
            showCurrentGame();
            board = null;
        } else if(board.hasErrors()){
            System.out.println("Seu jogo contém erros, verifique o seu board e faça os ajustes");
        } else {
            System.out.println("Você ainda precisa preencher algum espaço");
        }

    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while(current < min || current > max){
           System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }
}
