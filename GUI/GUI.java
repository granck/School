//look at to help with GameOfLife
//

public GUI(){
   this.grid = new Grid();

   //Should use thread timer instead!!
   this.timerListener = new TimerListener();
   this.timer = new Timer(400, timerListener);

   this.keyboardListener = new KeyboardListener();
   
   JFrame window = new JFrame("Game of Life");
   window.setSize(925, 1050);
   window.setVisible(true);
   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   window.setLayout(new BorderLayout());
   window.add(this,BorderLayout.Center);

   this.addMouseListener(mouse = new MouseListener());
   window.addKeyListener(keyboardListener);

   this.setBackgroundColor(Color.GREEN);

}//end constructor

@Override
public void paintComponent(Graphics g){
   int x;
   int y;
   for(int row = 0; row < 75....){
      for(int column = 0; column < 75....){
         //size of x and y will be based on a multiple of row and column

         //if cell is alive
         if(grid.isAlive(row, column))
            g.setColor
            g.fillRect
            g.drawRect

         //else cell is dead
         else
            g.setColor //different color
            g.fillRect
            g.drawRect

      }//end for
   }//end for
}//end method paintComponent

   private class MouseListener extends MouseAdapter{
      public void mouseClicked(MouseEvent e){
         //go to MouseEvent class to look up implementation
         //get x,y coordinate
         //Getting the x,y coordinates will be found by dividing the pixel by 
         //the same value that was multiplied earlier?
         //call a method that switches the state of the cell, then call repaint
      }//end method mouseClicked

   }//end inner class MouseListener
   private class TimerListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         //update board and repaint after time has passed
      }//end method actionPerformed
   }//end class TimerListener

   private class KeyboardListener extends KeyAdapter{
      public void keyTyped(KeyEvent e){
         //use (KeyEvent) e's method to determine what letter was pressed on the keyboard
         //if the letter was the one desgined to start the board, start the timer
         //else, if the letter was the one designed to stop, stop the timer
         //else if the letter was the one designed to save the state, call save method
         //else if the letter was the one designed to load a state, call load method
         //
      }//end method keyTyped
   }//end class KeyboardListener

