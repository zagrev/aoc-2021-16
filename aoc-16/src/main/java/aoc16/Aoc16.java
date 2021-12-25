/**
 *
 */
package aoc16;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 */
public class Aoc16
{

   /**
    * @param args
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static void main(final String[] args) throws FileNotFoundException, IOException
   {
      try (final BitReader input = new BitReader(new BufferedReader(new FileReader("input.txt"))))
      {
         final BitsPacket packet = readPacket(input);
         System.out.println("read packet: " + packet);
      }
   }

   /**
    * @param input
    * @return
    * @throws IOException
    */
   public static BitsPacket readPacket(final BitReader input) throws IOException
   {
      final BitsPacket packet = new BitsPacket();

      packet.setVersion(input.readBits(3));
      packet.setType(input.readBits(3));

      if (packet.getType() == 4)
      {
         int literal = 0;
         int next = 1;
         while (next > 0)
         {
            next = input.readBits(1);
            literal = literal << 4 | input.readHex();
         }
      }

      return packet;
   }

}
