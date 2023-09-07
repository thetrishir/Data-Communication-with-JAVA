package lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Receiver {
    String inputString;
    String outputString;
    FileWriter fw;
    FileReader fr;
    FileWriter fww;
    FileReader frr;
    int errorCount = 0;
    int successCount = 0;
    
    Receiver(int n) throws FileNotFoundException, IOException{
        frr = new FileReader("tempState.txt");
        fww = new FileWriter("tempStateBinary.txt");
        if(n==1){
            nrzI();
        }else if(n==2){
            nrzL();
        }else if(n==3){
            rz();
        }else if(n==4){
            manchester();
        }else if(n==5){
            diffManchester();
        }else if(n==6){
            ami();
        }else if(n==7){
            pseudoternary();
        }else if(n==8){
            b8zs();
        }else if(n==9){
            hdb3();
        }else if(n==10){
            b4b5();
        }else{
            nrzI();
        }
        fww.close();

        fr = new FileReader("tempStateBinary.txt");
        fw = new FileWriter("labOut.txt");
        BufferedReader br = new BufferedReader(fr);
        int i = 0;
        inputString = "";
        while(true){
            int x = br.read();
            i++;
            if(x == -1){
                physicalLayer(inputString);
                break;
            }
            char ch = (char) x;
            inputString = inputString + Character.toString(ch);
            if(i == 1200)
            {
                physicalLayer(inputString);
                i = 0;
                inputString = "";
            }
        }
        fw.close();
    }
    
    
    void physicalLayer(String s) throws IOException{
        outputString = "";
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;

        if (randomNumber < 21) {
            errorCount++;
            for(int i = 0; i<151; i++) {
            	outputString += "#";
            }
	        outputString = outputString.substring(4);
        } else {
        	successCount++;
	        int i;
	        for(i = 0 ; i < s.length();i+=8)
	        {
	            String temp = s.substring(i, i+8);
	            int x = Integer.parseInt(temp, 2);
	            char ch = (char) x;
	            outputString = outputString + Character.toString(ch);
	        }
	        outputString = outputString.substring(4);
        }
        dataLinkLayer(outputString);
    }
    
    void dataLinkLayer(String s) throws IOException{
        outputString = s.substring(3,s.length()-3);
        networkLayer(outputString);
        
    }
    
    void networkLayer(String s) throws IOException{
        outputString = s.substring(3);
        transportLayer(outputString);
    }
    
    void transportLayer(String s) throws IOException{
        outputString = s.substring(3);
        sessionLayer(outputString);
    }
    
    
    void sessionLayer(String s) throws IOException{
        outputString = s.substring(3);
        presentationLayer(outputString);
    }
    
    void presentationLayer(String s) throws IOException{
        outputString = s.substring(3);
        applicationLayer(outputString);
    }
    
    void applicationLayer(String s) throws IOException{
        outputString = s.substring(3);
        fw.write(outputString);
    }

    void nrzI() throws IOException{
        char prevState = '+';
        char state = '0';
        char antiState = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == prevState){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
            prevState = ch;
        }
    }

    void nrzL() throws IOException{
        char state = '0';
        char antiState = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == '+'){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
        }
    }

    void rz() throws IOException{
        char state = '0';
        char antiState = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 2400) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            int c = (char) x;
            x = brr.read();
            c = c - x;
            if(c == 2){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
        }
    }
    
    void manchester() throws IOException{
      char state = '0';
      char antiState = '1';
      BufferedReader brr = new BufferedReader(frr);
      int i = 0;
      inputString = "";
      while(true){
          int x = brr.read();
          i++;
          if(x == -1){
              fww.write(inputString);
              break;
          }
          if(i == 2400) {
              fww.write(inputString);
              i = 0;
              inputString = "";
          }
          int c = (char) x;
          x = brr.read();
          c = c - x;
//              System.out.println(" c "+c+"   ");
          if(c == -2){
              inputString = inputString + state;
          }else{
              inputString = inputString + antiState;
          }
      }
  }
    
  void diffManchester() throws IOException{
        int prevState = -2;
        char state = '0';
        char antiState = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 2400) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            int c = (char) x;
            x = brr.read();
            c = c - x;
            if(c == prevState){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
            prevState = c;
        }
  }



    void ami() throws IOException{
        char zero = '0';
        char one = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == '0'){
                inputString = inputString + zero;
            }else{
                inputString = inputString + one;
            }
            System.out.println("receiver  "  + inputString);
        }
    }

    void pseudoternary() throws IOException{
        char zero = '0';
        char one = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == '1'){
                inputString = inputString + one;
            }else{
                inputString = inputString + zero;
            }
            System.out.println("receiver  "  + inputString);
        }

    }


    void b8zs() throws IOException{
        char zero = '0';
        char one = '1';
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == '0'){
                inputString = inputString + zero;
                if (inputString.contains("00011011")){
                    inputString = inputString.replace("00011011","00000000");
                }
            }else{
                inputString = inputString + one;
            }
//            System.out.println("receiver i " + i + "  zeros " + countZeros + "  ch " + ch + "    " + inputString);
        }
    }

    void hdb3() throws IOException{
        char zero = '0';
        char one = '1';
        int nonZeroPulse = 0;
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            char ch = (char) x;
            Character.toString(ch);
            if(ch == '0'){
                inputString = inputString + zero;
                if (nonZeroPulse % 2 == 0){
                    inputString = inputString.replace("B00V","0000");
                }
                else {
                    inputString = inputString.replace("000V","0000");
                }
            }else if(ch == '-' || ch == '+'){
                inputString = inputString + one;
                nonZeroPulse++;
            }else {
                inputString = inputString + ch;
            }
//            System.out.println("receiver i " + i + "  ones " + nonZeroPulse + "  ch " + ch + "    " + inputString);
        }
    }

    void b4b5() throws IOException{
        String tempString = "";
        BufferedReader brr = new BufferedReader(frr);
        int i = 0;
        inputString = "";
        while(true){
            int x = brr.read();
            i++;
            if(x == -1){
                fww.write(inputString);
                break;
            }
            char ch = (char) x;
            Character.toString(ch);
            tempString = tempString + ch;
//            System.out.print(tempString + " ");
            if(i % 5 == 0){
                if (tempString.equals("11110")){
                    inputString = inputString + "0000";
                }else if (tempString.equals("01001")){
                    inputString = inputString + "0001";
                }else if (tempString.equals("10100")){
                    inputString = inputString + "0010";
                }else if (tempString.equals("10101")){
                    inputString = inputString + "0011";
                }else if (tempString.equals("01010")){
                    inputString = inputString + "0100";
                }else if (tempString.equals("01011")){
                    inputString = inputString + "0101";
                }else if (tempString.equals("01110")){
                    inputString = inputString + "0110";
                }else if (tempString.equals("01111")){
                    inputString = inputString + "0111";
                }else if (tempString.equals("10010")){
                    inputString = inputString + "1000";
                }else if (tempString.equals("10011")){
                    inputString = inputString + "1001";
                }else if (tempString.equals("10110")){
                    inputString = inputString + "1010";
                }else if (tempString.equals("10111")){
                    inputString = inputString + "1011";
                }else if (tempString.equals("11010")){
                    inputString = inputString + "1100";
                }else if (tempString.equals("11011")){
                    inputString = inputString + "1101";
                }else if (tempString.equals("11100")){
                    inputString = inputString + "1110";
                }else if (tempString.equals("11101")){
                    inputString = inputString + "1111";
                }
                tempString = "";
            }
            if(i == 1200) {
                fww.write(inputString);
                i = 0;
                inputString = "";
            }
            System.out.println( "  inputString "+inputString);
        }
    }
}
    
