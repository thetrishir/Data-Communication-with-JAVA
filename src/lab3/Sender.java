package lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Sender {
    String inputString;
    String outputString;
    FileWriter fw;
    FileWriter fww;
    FileReader fr;
    FileReader frr;
    int count = 0;
    int n;
    
    
    Sender(int n) throws FileNotFoundException, IOException{
        fr = new FileReader("labIn.txt");
        fw = new FileWriter("tempBinary.txt");
        fww = new FileWriter("tempState.txt");
        BufferedReader br = new BufferedReader(fr);
        int i = 0;
        inputString = "";
        while(true){
            int x = br.read();
            i++;
            if(x == -1){
                applicationLayer(inputString, n);
                break;
            }
            char ch = (char) x;
            inputString = inputString + Character.toString(ch);
            if(i == 125)
            {
                applicationLayer(inputString, n);
                i = 0;
                inputString = "";
            }
        }
        fw.close();
        fww.close();
    }
    
    
    void applicationLayer(String s, int n) throws IOException{
        String mod_s = "A-H" + s;
        presentationLayer(mod_s, n);
    }
    
    void presentationLayer(String s, int n) throws IOException{
        String mod_s = "P-H" + s;
        sessionLayer(mod_s, n);
    }
    
    void sessionLayer(String s, int n) throws IOException{
        String mod_s = "S-H" + s;
        transportLayer(mod_s, n);
    }
    
    void transportLayer(String s, int n) throws IOException{
        String mod_s = "T-H" + s;
        networkLayer(mod_s, n);
    }
    
    void networkLayer(String s, int n) throws IOException{
        String mod_s = "N-H" + s;
        dataLinkLayer(mod_s, n);
    }
    
    void dataLinkLayer(String s, int n) throws IOException{
        String mod_s = "D-H" + s + "D-T";
        physicalLayer(mod_s, n);
    }
    
    void physicalLayer(String s, int n) throws IOException{
        String mod_s = "PH-H" + s;
        outputString = "";
        for(int i = 0 ; i < mod_s.length();i++){
            char c = mod_s.charAt(i);
            String sr = Integer.toBinaryString(c);
            int sr_len = sr.length();
            if(sr_len != 8)
            {
                for(int j = 0 ; j< 8 - sr_len ;j++){
                sr = "0" + sr;
                }
            }

            outputString = outputString + sr;
        }
        fw.write(outputString);
//        fw.close();
        count++;

//        outputString = "01010111000000001100110000101011";
        if(n==1){
            System.out.println("Transmitting through NRZ-I method");
            nrzI(outputString);
        }else if(n==2){
            System.out.println("Transmitting through NRZ-L method");
            nrzL(outputString);
        }else if(n==3){
            System.out.println("Transmitting through RZ method");
            rz(outputString);
        }else if(n==4){
            System.out.println("Transmitting through Manchester method");
            manchester(outputString);
        }else if(n==5){
            System.out.println("Transmitting through Differential Manchester method");
            diffManchester(outputString);
        }else if(n==6){
            System.out.println("Transmitting through B8ZS method");
            ami(outputString);
        }else if(n==7){
            System.out.println("Transmitting through B8ZS method");
            pseudoternary(outputString);
        }else if(n==8){
            System.out.println("Transmitting through B8ZS method");
            b8zs(outputString);
        }else if(n==9){
            System.out.println("Transmitting through HDB3 method");
            hdb3(outputString);
        }else if(n==10){
            System.out.println("Transmitting through 4B5B method");
            b4b5(outputString);
        }else{
            System.out.println("Transmitting through NRZ-I method as default");
            nrzI(outputString);
        }
    }

    void nrzI(String s) throws IOException{
        char state = '+';
        char antiState = '-';
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
                char temp = state;
                state = antiState;
                antiState = temp;
            }
        }
        fww.write(inputString);
    }
    
    void nrzL(String s) throws IOException{
        char state = '+';
        char antiState = '-';
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
        }
        fww.write(inputString);
    }

    void rz(String s) throws IOException{
        String state = "-+";
        String antiState = "+-";
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
        }
        fww.write(inputString);
    }
    
    void manchester(String s) throws IOException{
        String state = "+-";
        String antiState = "-+";
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
            }
        }
        fww.write(inputString);
    }
    
    void diffManchester(String s) throws IOException{
        String state = "+-";
        String antiState = "-+";
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                inputString = inputString + state;
            }else{
                inputString = inputString + antiState;
                String temp = state;
                state = antiState;
                antiState = temp;
            }
        }
        fww.write(inputString);
    }

    void ami(String s) throws IOException{
        String pos = "+";
        String neg = "-";
        String zero = "0";
        String prevState = "";
        int countZeros = 0;
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                countZeros++;
                if (countZeros == 8){
                    if(prevState == pos){
                        inputString = inputString.replace("0000000","000+-0-+");
                        prevState = pos;
                    }else{
                        inputString = inputString.replace("0000000","000-+0+-");
                        prevState = neg;
                    }
                }else{
                    inputString = inputString + zero;
                }
            }else{
                if(prevState == pos){
                    inputString = inputString + neg;
                    prevState = neg;
                }else{
                    inputString = inputString + pos;
                    prevState = pos;
                }
                countZeros = 0;
            }
//            System.out.println("j "+ j+ " zero "+countZeros +" Ch "+ ch+ "   "+inputString);
        }
        fww.write(inputString);
    }


    void pseudoternary(String s) throws IOException{

    }


    void b8zs(String s) throws IOException{
        String pos = "+";
        String neg = "-";
        String zero = "0";
        String prevState = "";
        int countZeros = 0;
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                countZeros++;
                if (countZeros == 8){
                    if(prevState == pos){
                        inputString = inputString.replace("0000000","000+-0-+");
                        prevState = pos;
                    }else{
                        inputString = inputString.replace("0000000","000-+0+-");
                        prevState = neg;
                    }
                }else{
                    inputString = inputString + zero;
                }
            }else{
                if(prevState == pos){
                    inputString = inputString + neg;
                    prevState = neg;
                }else{
                    inputString = inputString + pos;
                    prevState = pos;
                }
                countZeros = 0;
            }
//            System.out.println("j "+ j+ " zero "+countZeros +" Ch "+ ch+ "   "+inputString);
        }
        fww.write(inputString);
    }

    void hdb3(String s) throws IOException{
        String pos = "+";
        String neg = "-";
        String zero = "0";
        String prevState = "";
        int countZeros = 0;
        int nonZeroPulse = 0;
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            if(ch == 0){
                countZeros++;
                if (countZeros == 4){
                    inputString = inputString + zero;
                    if(nonZeroPulse % 2 == 0){
                        if(prevState == pos){
                            inputString = inputString.replace("0000","B00V");
                            prevState = neg;
                        }else{
                            inputString = inputString.replace("0000","B00V");
                            prevState = pos;
                        }
                        nonZeroPulse = nonZeroPulse + 2;
                    }else{
                        if(prevState == pos){
                            inputString = inputString.replace("0000","000V");
                            prevState = pos;
                        }else{
                            inputString = inputString.replace("0000","000V");
                            prevState = neg;
                        }
                        nonZeroPulse++;
                    }
                    countZeros = 0;
                }else{
                    inputString = inputString + zero;
                }
            }else{
                if(prevState == pos){
                    inputString = inputString + neg;
                    prevState = neg;
                }else{
                    inputString = inputString + pos;
                    prevState = pos;
                }
                countZeros = 0;
                nonZeroPulse++;
            }
//            System.out.println("j "+ j+ " zero "+countZeros +" Ch "+ ch+ "   "+inputString);
        }
        fww.write(inputString);
    }

    void b4b5(String s) throws IOException{
        String tempString = "";
        inputString = "";
        for(int j = 0 ; j < s.length(); j++) {
            char h = (char) s.charAt(j);
            int ch = Integer.parseInt(Character.toString(h));
            tempString = tempString + ch;
//            System.out.println(tempString + " " + " count "+j+1);
            if((j+1) % 4 == 0){
                if (tempString.equals("0000")){
                    inputString = inputString + "11110";
//                    System.out.println( "  inputString "+inputString);
                }else if (tempString.equals("0001")){
                    inputString = inputString + "01001";
                }else if (tempString.equals("0010")){
                    inputString = inputString + "10100";
                }else if (tempString.equals("0011")){
                    inputString = inputString + "10101";
                }else if (tempString.equals("0100")){
                    inputString = inputString + "01010";
                }else if (tempString.equals("0101")){
                    inputString = inputString + "01011";
                }else if (tempString.equals("0110")){
                    inputString = inputString + "01110";
                }else if (tempString.equals("0111")){
                    inputString = inputString + "01111";
                }else if (tempString.equals("1000")){
                    inputString = inputString + "10010";
                }else if (tempString.equals("1001")){
                    inputString = inputString + "10011";
                }else if (tempString.equals("1010")){
                    inputString = inputString + "10110";
                }else if (tempString.equals("1011")){
                    inputString = inputString + "10111";
                }else if (tempString.equals("1100")){
                    inputString = inputString + "11010";
                }else if (tempString.equals("1101")){
                    inputString = inputString + "11011";
                }else if (tempString.equals("1110")){
                    inputString = inputString + "11100";
                }else if (tempString.equals("1111")){
                    inputString = inputString + "11101";
                }
                tempString = "";
            }
        }
        System.out.println(inputString);
        fww.write(inputString);
    }
}
