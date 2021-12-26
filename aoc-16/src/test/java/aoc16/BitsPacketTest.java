/**
 *
 */
package aoc16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 */
class BitsPacketTest
{

   /**
    * @param expectedPackets
    * @param actualPackets
    */
   private void comparePackets(final BitsPacket[] expectedPackets, final BitsPacket[] actualPackets)
   {
      final int minLength = Math.min(expectedPackets.length, actualPackets.length);
      for (int i = 0; i < minLength; i++)
      {
         assertEquals(expectedPackets[i].getVersion(), actualPackets[i].getVersion(),
               "Version mismatch on packet " + i);
         assertEquals(expectedPackets[i].getType(), actualPackets[i].getType(), "Type mismatch on packet " + i);
         assertEquals(expectedPackets[i].getLiteral(), actualPackets[i].getLiteral(),
               "Literal mismatch on packet " + i);
         assertEquals(expectedPackets[i].getBitlength(), actualPackets[i].getBitlength(),
               "Bitlength mismatch on packet " + i);
      }
      assertEquals(expectedPackets.length, actualPackets.length, "Expected and Actual array lengths did not match");
   }

   /**
    * @throws IOException
    */
   @Test
   void testSample() throws IOException
   {
      final String input = "D2FE28";
      final int expectedVersion = 6;
      final int expectedType = 4;
      final int expectedLiteral = 2021;
      final int expectedBitsRead = 21;

      try (BitReader reader = new BitReader(new StringReader(input)))
      {
         final BitsPacket packet = Aoc16.readPacket(reader);

         assertEquals(expectedVersion, packet.getVersion());
         assertEquals(expectedType, packet.getType());
         assertEquals(expectedLiteral, packet.getLiteral());
         assertEquals(expectedBitsRead, packet.getBitlength());

         assertEquals(0, packet.subPackets.size());

         final int sum = Aoc16.getVersionSum(packet);
         assertEquals(6, sum);
      }
   }

   /**
    * @throws IOException
    */
   @Test
   void testSample2() throws IOException
   {
      final String input = "38006F45291200";
      final int expectedVersion = 1;
      final int expectedType = 6;
      final int expectedBitsRead = 49;

      final BitsPacket[] expectedPackets =
      { new BitsPacket(6, 10, 11), new BitsPacket(2, 20, 16) };

      try (BitReader reader = new BitReader(new StringReader(input)))
      {
         final BitsPacket packet = Aoc16.readPacket(reader);

         assertEquals(expectedVersion, packet.getVersion());
         assertEquals(expectedType, packet.getType());
         assertEquals(expectedBitsRead, packet.getBitlength());

         comparePackets(expectedPackets, packet.subPackets.toArray(new BitsPacket[packet.subPackets.size()]));

         final int sum = Aoc16.getVersionSum(packet);
         assertEquals(9, sum);
      }
   }

   /**
    * @throws IOException
    */
   @Test
   void testSample3() throws IOException
   {
      final String input = "EE00D40C823060";
      final int expectedVersion = 7;
      final int expectedType = 3;
      final int expectedBitsRead = 51;

      final BitsPacket[] expectedPackets =
      { new BitsPacket(2, 1, 11), new BitsPacket(4, 2, 11), new BitsPacket(1, 3, 11) };

      try (BitReader reader = new BitReader(new StringReader(input)))
      {
         final BitsPacket packet = Aoc16.readPacket(reader);

         assertEquals(expectedVersion, packet.getVersion());
         assertEquals(expectedType, packet.getType());
         assertEquals(expectedBitsRead, packet.getBitlength());

         comparePackets(expectedPackets, packet.subPackets.toArray(new BitsPacket[packet.subPackets.size()]));

         final int sum = Aoc16.getVersionSum(packet);
         assertEquals(14, sum);
      }
   }

   /**
    * @param input
    * @param expectedSum
    * @throws IOException
    */
   @ParameterizedTest
   @CsvSource(
   { "8A004A801A8002F478, 16", "620080001611562C8802118E34, 12", "C0015000016115A2E0802F182340, 23",
         "A0016C880162017C3686B18A3D4780, 31" })
   void testSampleSum(final String input, final int expectedSum) throws IOException
   {
      try (BitReader reader = new BitReader(new StringReader(input)))
      {
         final BitsPacket packet = Aoc16.readPacket(reader);
         final int actualSum = Aoc16.getVersionSum(packet);

         assertEquals(expectedSum, actualSum);
      }
   }

}
