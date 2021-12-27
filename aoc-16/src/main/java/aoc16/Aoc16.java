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
    * @param p
    * @return the sum of this packet and all the sub-packets
    */
   public static int getVersionSum(final BitsPacket p)
   {
      int sum = 0;

      sum += p.getVersion();
      for (final BitsPacket sub : p.subPackets)
      {
         sum += getVersionSum(sub);
      }

      return sum;
   }

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
         System.out.println("version sum = " + getVersionSum(packet));
         System.out.println("value = " + packet.getValue());
      }
   }

   /**
    * @param input
    *           the input to read
    * @param p
    *           the packet to update
    * @return the number of bits read
    * @throws IOException
    */
   private static int readLiteral(final BitReader input, final BitsPacket p) throws IOException
   {
      int numBitsRead = 0;
      long literal = 0;
      int next = 1;

      while (next > 0)
      {
         next = input.readBits(1);
         literal = literal << 4 | input.readBits(4);
         numBitsRead += 5;
      }
      p.setLiteral(literal);

      return numBitsRead;
   }

   /**
    * @param input
    * @return the fully read packet from the input
    * @throws IOException
    */
   public static BitsPacket readPacket(final BitReader input) throws IOException
   {
      final int lengthType;
      final int lengthBits;
      int length;

      final BitsPacket packet = new BitsPacket();
      int numBitsRead = 0;

      packet.setVersion(input.readBits(3));
      packet.setType(input.readBits(3));
      numBitsRead += 6;

      // literal packet
      if (packet.getType() == 4)
      {
         numBitsRead += readLiteral(input, packet);
      }

      // operator packet
      else
      {
         lengthType = input.readBits(1);
         numBitsRead += 1;
         lengthBits = lengthType == 0 ? 15 : 11;
         length = input.readBits(lengthBits);
         numBitsRead += lengthBits;

         // length type 0 means read 'length' bits
         if (lengthType == 0)
         {
            while (length > 0)
            {
               final BitsPacket subPacket = readPacket(input);
               packet.subPackets.add(subPacket);

               length -= subPacket.getBitlength();
               numBitsRead += subPacket.getBitlength();
            }

            // just test that we are still on track
            if (length < 0)
            {
               throw new IllegalArgumentException("Too many bits read: " + length);
            }
         }
         else // length type id == 0
         {
            for (int i = 0; i < length; i++)
            {
               final BitsPacket newP = readPacket(input);

               packet.subPackets.add(newP);
               numBitsRead += newP.getBitlength();
            }
         }
      }

      packet.setBitlength(numBitsRead);
      return packet;
   }
}
