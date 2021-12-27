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
class BitsOperatorTest
{

   /**
    * @param input
    * @param expectedValue
    * @throws IOException
    */
   @ParameterizedTest
   @CsvSource(
   { "C200B40A82, 3", "04005AC33890, 54", "880086C3E88112, 7", "CE00C43D881120, 9", "D8005AC2A8F0, 1", "F600BC2D8F, 0",
         "9C005AC2F8F0, 0", "9C0141080250320F1802104A08, 1" })
   public void test(final String input, final int expectedValue) throws IOException
   {
      try (BitReader reader = new BitReader(new StringReader(input)))
      {
         final BitsPacket p = Aoc16.readPacket(reader);

         assertEquals(expectedValue, p.getValue());
      }
   }

   /**
    *
    */
   @Test
   public void testEquals()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.EQUAL);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 4, 1));

      assertEquals(1, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.EQUAL);
      p2.subPackets.add(new BitsPacket(1, 9, 1));
      p2.subPackets.add(new BitsPacket(1, 4, 1));

      assertEquals(0, p2.getValue());
   }

   /**
    *
    */
   @Test
   public void testGreaterThan()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.GT);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 9, 1));

      assertEquals(0, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.GT);
      p2.subPackets.add(new BitsPacket(1, 9, 1));
      p2.subPackets.add(new BitsPacket(1, 4, 1));

      assertEquals(1, p2.getValue());
   }

   /**
    *
    */
   @Test
   public void testLessThan()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.LT);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 9, 1));

      assertEquals(1, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.LT);
      p2.subPackets.add(new BitsPacket(1, 9, 1));
      p2.subPackets.add(new BitsPacket(1, 4, 1));

      assertEquals(0, p2.getValue());
   }

   /**
    *
    */
   @Test
   public void testMax()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.MAX);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 2, 1));
      p.subPackets.add(new BitsPacket(1, 9, 1));

      assertEquals(9, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.MAX);
      p2.subPackets.add(new BitsPacket(1, 778514865480L, 1));
      p2.subPackets.add(new BitsPacket(1, Integer.MAX_VALUE, 1));

      assertEquals(778514865480L, p2.getValue());

      final BitsPacket p3 = new BitsPacket(1, BitsPacket.MAX);
      p3.subPackets.add(new BitsPacket(1, 778514865480L, 1));

      assertEquals(778514865480L, p3.getValue());
   }

   /**
    *
    */
   @Test
   public void testMin()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.MIN);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 2, 1));
      p.subPackets.add(new BitsPacket(1, 9, 1));

      assertEquals(2, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.MIN);
      p2.subPackets.add(new BitsPacket(1, 778514865480L, 1));
      p2.subPackets.add(new BitsPacket(1, Integer.MAX_VALUE, 1));

      assertEquals(Integer.MAX_VALUE, p2.getValue());

      final BitsPacket p3 = new BitsPacket(1, BitsPacket.MIN);
      p3.subPackets.add(new BitsPacket(1, 778514865480L, 1));

      assertEquals(778514865480L, p3.getValue());
   }

   /**
    *
    */
   @Test
   public void testProduct()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.PRODUCT);
      p.subPackets.add(new BitsPacket(1, 4, 1));
      p.subPackets.add(new BitsPacket(1, 2, 1));

      assertEquals(8, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.PRODUCT);
      p2.subPackets.add(new BitsPacket(1, 4, 1));
      p2.subPackets.add(new BitsPacket(1, 2, 1));
      p2.subPackets.add(new BitsPacket(1, 7, 1));

      assertEquals(56, p2.getValue());
   }

   /**
    *
    */
   @Test
   public void testSum()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.SUM);
      p.subPackets.add(new BitsPacket(1, 1, 1));
      p.subPackets.add(new BitsPacket(1, 2, 1));

      assertEquals(3, p.getValue());

      final BitsPacket p2 = new BitsPacket(1, BitsPacket.SUM);
      p2.subPackets.add(new BitsPacket(1, 1, 1));
      p2.subPackets.add(new BitsPacket(1, 2, 1));
      p2.subPackets.add(new BitsPacket(1, 3, 1));

      assertEquals(6, p2.getValue());
   }

   /**
    *
    */
   @Test
   public void testSumSingle()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.SUM);
      p.subPackets.add(new BitsPacket(1, 2, 1));

      assertEquals(2, p.getValue());
   }

   /**
    * ((676445)*((14)>(102004)))
    */
   @Test
   public void testTricky1()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.PRODUCT);
      p.subPackets.add(new BitsPacket(1, 676445, 1));

      final BitsPacket pGt = new BitsPacket(1, BitsPacket.GT);
      p.subPackets.add(pGt);

      pGt.subPackets.add(new BitsPacket(0, 14, 1));
      pGt.subPackets.add(new BitsPacket(1, 102004, 0));

      final long value = p.getValue();
      assertEquals(0, value);
   }

   /**
    * (min((((min(max(min(max((max(((min(min(max((min(min(min(min(778514865480)))))))))))))))))))))
    */
   @Test
   public void testTricky2()
   {
      final BitsPacket p = new BitsPacket(1, BitsPacket.MIN);

      BitsPacket lastPacket = p;

      for (int i = 0; i < 5; i++)
      {
         final BitsPacket newChild = new BitsPacket(1, i % 2 == 0 ? BitsPacket.MIN : BitsPacket.MAX);
         lastPacket.subPackets.add(newChild);
         lastPacket = newChild;
      }
      lastPacket.subPackets.add(new BitsPacket(1, 778514865480L, 1));

      final long value = p.getValue();
      assertEquals(778514865480L, value);
   }
}
