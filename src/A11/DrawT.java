package A11;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

class DrawT {
    DrawT() {
    }

    public static void main(String[] args) {
        String M = args[0];
        String T = "";
        Scanner input = new Scanner(System.in);
        if (args.length == 1) {
            if (!args[0].equals("w")) {
                System.out.println("error");
            }
        } else if (args.length != 2) {
            System.out.println("Error");
        }

        while(true) {
            int L;
            do {
                System.out.println(args[1]);
                if (M.equals("w")) {
                    L = Integer.parseInt(JOptionPane.showInputDialog("Give me a number "));
                } else {
                    L = Integer.parseInt(args[1]);
                }

                if (L < 5 || L > 20) {
                    System.out.println("Ooops something went wrong with your integer");
                    System.out.println(L);
                    System.exit(0);
                }

                if (!M.equals("c") && !M.equals("w") && !M.equals("f") && !M.equals("g")) {
                    System.out.println("Ooops something went wrong with your letter");
                    System.exit(0);
                }

                int i;
                int j;
                if (M.equals("c")) {
                    for ( i = 0; i < L; i++) {
                            for ( j = 0; j < L; j++) {
                                if (i >0 && j == L / 2) {
                                System.out.print('*');
                                } else if (i==0) {
                                    System.out.print('*');

                                } else {
                                    System.out.print(" ");
                                }
                            }
                            System.out.println();
                    }
                }


                if (M.equals("w")) {
                    T = "";
                    i = 0;

                    while(true) {
                        if (i >= L) {
                            JOptionPane.showMessageDialog((Component)null, T, "Exit Window", 1);
                            break;
                        }

                        if (i != 0 && i != L / 2 && i != L - 1) {
                            T = T + "* \n";
                        } else {
                            for(j = 0; j < L; ++j) {
                                T = T + '*';
                            }

                            T = T + '\n';
                        }

                        ++i;
                    }
                }

                if (M.equals("f")) {
                    try {
                        PrintWriter writer = new PrintWriter("T.html", "UTF-8");
                        writer.println("<!DOCTYPE html> ");
                        writer.println("<html> ");
                        writer.println("<head>");
                        writer.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"/>");
                        writer.println("</head> ");
                        writer.println("<body><font size=\"" + L + "\">T with font size =" + L + "</font></body> ");
                        writer.println("</html>");
                        writer.close();
                    } catch (Exception var7) {
                        System.out.println("Πρόβλημα: " + var7);
                    }
                }

                if (M.equals("g")) {
                    drawTgraphics(L);
                }

                args[1] = String.valueOf(Integer.parseInt(args[1]) - 1);
                if (!M.equals("f") && !M.equals("g")) {
                    main(args);
                }

                if (!M.equals("w")) {
                    System.out.println("Give a new L ");
                    L = input.nextInt();
                }
            } while(L >= 5 && L <= 20);

            System.exit(0);
        }
    }

    static void drawTgraphics(int L) {
        Frame f = new Frame("Ζωγραυίζοντας το T") {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.draw(new Line2D.Double(50.0, 300.0, 200.0, 50.0));
                g2.drawString("T", 100,100);
            }
        };
        f.setSize(400, 400);
        f.setVisible(true);
    }
}


