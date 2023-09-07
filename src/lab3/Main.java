package lab3;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("       --Main Menu--");
        System.out.println("1. NRZ-I\n2. NRZ-L\n3. RZ\n4. Manchester\n5. Differential Manchester\n6. AMI\n7. Pseudoternary\n8. B8ZS\n9. HDB3\n10. 4B5B");
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of transmission method: ");
        int transmissionSystem = in.nextInt();
        System.out.println("");

        Sender s = new Sender(transmissionSystem);
        Receiver r = new Receiver(transmissionSystem);

        System.out.println("\nTrasmission complete");
        System.out.println(s.count+" Success count "+r.successCount+" and Error count "+r.errorCount);
        System.out.println("SNR: "+ (float) r.successCount/r.errorCount);
        System.out.println( r.successCount);
        System.out.println(r.errorCount);
    }
}
