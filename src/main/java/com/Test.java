package com;

import com.minesweeper.Game;
import com.minesweeper.Position;

public class Test {
    public static void main(String[] args) {
        Game game =new Game();
       Position p= game.parsePosition("A1", 4);//10
       System.out.println(p.row() + "," + p.col());
       Position p1= game.parsePosition("B1", 4);//10
       System.out.println(p1.row() + "," + p1.col());
;
        }
    
}
