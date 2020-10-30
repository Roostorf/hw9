package edu.ics211.h09;

import java.util.ArrayList;

/**
 * Class for recursively finding a solution to a Hexadecimal Sudoku problem.
 *
 * @author Biagioni, Edoardo, Cam Moore
 *     date August 5, 2016
 *     missing solveSudoku, to be implemented by the students in ICS 211
 */
public class HexadecimalSudoku {

  /**
   * Find an assignment of values to sudoku cells that makes the sudoku valid.
   *
   * @param sudoku the sudoku to be solved.
   * @return whether a solution was found if a solution was found, the sudoku is
   *         filled in with the solution if no solution was found, restores the
   *         sudoku to its original value.
   */
  public static boolean solveSudoku(int[][] sudoku) {
    // TODO: Implement this method recursively. You may use a recursive helper method.
    ArrayList<Integer> legalVal = new ArrayList<Integer>();
    
    // loop over all the rows
    for (int row = 0; row < sudoku.length; row++ ) {    
    //  loop over all the columns
      for (int col = 0; col < sudoku[row].length; col++ ) {
        
        
        
    //    if sudoku [row][col] is empty -1
        if (sudoku [row][col] == -1 ) {
    //      get the legal values for row, col
            legalVal = legalValues(sudoku, row, col);
        }
    //      if no legal values return false; base case 2.
        if (legalVal == null) {
          return false;
          
        } else { //      else loop over the legal values
          // probably where my problem
          int before = sudoku[row][col];
          
          for (int n = 0; n < legalVal.size(); n++) {
            //        set sudoku [row][col] to legal value
            sudoku[row][col] = legalVal.get(n);
            if (solveSudoku(sudoku)) {
              return true;
            } else {
              //restores to original value
              sudoku[row][col] = before;
            }
          }
        }

      }
    }
    
    
    return checkSudoku(sudoku, false); // base case 1;

  }

  /**
   * Find the legal values for the given sudoku and cell.
   *
   * @param sudoku the sudoku being solved.
   * @param row the row of the cell to get values for.
   * @param col the column of the cell.
   * @return an ArrayList of the valid values.
   */
  public static ArrayList<Integer> legalValues(int[][] sudoku, int row, int column) {
    // TODO: Implement this method. You may want to look at the checkSudoku method
    // to see how it finds conflicts.
    
    // Create an arraylist
    int r = 0;
    int c = 0;
    
    if(row < 4) {
      r = 0;
    } else if(row < 8) {
      r = 4;
    } else if(row < 12) {
      r = 8;
    } else if(row < 16) {
      r = 12;
    }
    
    if(column < 4) {
      r = 0;
    } else if(column < 8) {
      r = 4;
    } else if(column < 12) {
      r = 8;
    } else if(column < 16) {
      r = 12;
  }
    
    
    ArrayList<Integer> legalVals = new ArrayList<Integer>();
    // Fill it with all the values from 0 to F
    for (int k = 0; k < 16; k++) {
      legalVals.add(k);
    }
    // Remove all the values for the list that are in the row
    
    //System.out.println(sudoku.length + "sudoku length");
    
    for (int l = 0; l < sudoku.length; l++) {
      
      //System.out.println(legalVals.size() + "Legal Vals Size");
      
      if (legalVals.contains(sudoku[row][l])) {
        
       // System.out.println(legalVals.size() + "Legal Vals Size");
        legalVals.remove(legalVals.indexOf(sudoku[row][l]));
      }
    }
    
    
    // Remove all the values for the list that are in the column
    
    for (int l = 0; l < sudoku[row].length; l++) {
      if (legalVals.contains(sudoku[l][column])) {
        legalVals.remove(legalVals.indexOf(sudoku[l][column]));
      }
    }
    
    // Remove all the values for the list that are in the 4 x 4 grid
    for(int l = 0; l < sudoku.length / 4; l++) {
      for(int m = 0; m < sudoku[l].length / 4; m++) {
        if(legalVals.contains(sudoku[r + l][c + m])) {
          legalVals.remove(legalVals.indexOf(sudoku[r + l][c + m]));
        }

    }  
    }
    
    // if the list size is 0 return null
    if (legalVals.size() == 0) {
      return null;
    }
    
    // return list
    return legalVals;
  }


  /**
   * checks that the sudoku rules hold in this sudoku puzzle. cells that contain
   * 0 are not checked.
   *
   * @param sudoku the sudoku to be checked.
   * @param printErrors whether to print the error found, if any.
   * @return true if this sudoku obeys all of the sudoku rules, otherwise false.
   */
  public static boolean checkSudoku(int[][] sudoku, boolean printErrors) {
    if (sudoku.length != 16) {
      if (printErrors) {
        System.out.println("sudoku has " + sudoku.length + " rows, should have 16");
      }
      return false;
    }
    for (int i = 0; i < sudoku.length; i++) {
      if (sudoku[i].length != 16) {
        if (printErrors) {
          System.out.println("sudoku row " + i + " has "
              + sudoku[i].length + " cells, should have 16");
        }
        return false;
      }
    }
    /* check each cell for conflicts */
    for (int i = 0; i < sudoku.length; i++) {
      for (int j = 0; j < sudoku.length; j++) {
        int cell = sudoku[i][j];
        if (cell == -1) {
          continue; /* blanks are always OK */
        }
        if ((cell < 0) || (cell > 16)) {
          if (printErrors) {
            System.out.println("sudoku row " + i + " column " + j
                + " has illegal value " + String.format("%02X", cell));
          }
          return false;
        }
        /* does it match any other value in the same row? */
        for (int m = 0; m < sudoku.length; m++) {
          if ((j != m) && (cell == sudoku[i][m])) {
            if (printErrors) {
              System.out.println("sudoku row " + i + " has " + String.format("%X", cell)
                  + " at both positions " + j + " and " + m);
            }
            return false;
          }
        }
        /* does it match any other value it in the same column? */
        for (int k = 0; k < sudoku.length; k++) {
          if ((i != k) && (cell == sudoku[k][j])) {
            if (printErrors) {
              System.out.println("sudoku column " + j + " has " + String.format("%X", cell)
                  + " at both positions " + i + " and " + k);
            }
            return false;
          }
        }
        /* does it match any other value in the 4x4? */
        for (int k = 0; k < 4; k++) {
          for (int m = 0; m < 4; m++) {
            int testRow = (i / 4 * 4) + k; /* test this row */
            int testCol = (j / 4 * 4) + m; /* test this col */
            if ((i != testRow) && (j != testCol) && (cell == sudoku[testRow][testCol])) {
              if (printErrors) {
                System.out.println("sudoku character " + String.format("%X", cell) + " at row "
                    + i + ", column " + j + " matches character at row " + testRow + ", column "
                    + testCol);
              }
              return false;
            }
          }
        }
      }
    }
    return true;
  }


  /**
   * Converts the sudoku to a printable string.
   *
   * @param sudoku the sudoku to be converted.
   * @param debug whether to check for errors.
   * @return the printable version of the sudoku.
   */
  public static String toString(int[][] sudoku, boolean debug) {
    if ((!debug) || (checkSudoku(sudoku, true))) {
      String result = "";
      for (int i = 0; i < sudoku.length; i++) {
        if (i % 4 == 0) {
          result = result + "+---------+---------+---------+---------+\n";
        }
        for (int j = 0; j < sudoku.length; j++) {
          if (j % 4 == 0) {
            result = result + "| ";
          }
          if (sudoku[i][j] == -1) {
            result = result + "  ";
          } else {
            result = result + String.format("%X", sudoku[i][j]) + " ";
          }
        }
        result = result + "|\n";
      }
      result = result + "+---------+---------+---------+---------+\n";
      return result;
    }
    return "illegal sudoku";
  }
}
