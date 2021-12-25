/**
 *
 */
package aoc16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 */
class TestBitReader
{

   /**
    * @param hex
    * @param expected
    * @throws IOException
    */
   @ParameterizedTest
   @CsvSource(
   { "1, 1", "2, 2", "3, 3", "4, 4", "5, 5", "6, 6", "7,7", "8,8", "9,9", "A,10", "B,11", "C,12", "D,13", "E,14",
         "F,15" })
   void testReadBit(final String hex, final int expected) throws IOException
   {
      try (BitReader input = new BitReader(new StringReader(hex)))
      {
         final int value = input.readBits(4);
         System.out.format("value = %2d, %2X %n", value, value);
         assertEquals(expected, value);
      }
   }

   /**
    * @throws IOException
    */
   @ParameterizedTest
   @CsvSource(
   { "1234, 1234", "AA, 2800" })
   void testReadHex(final String hex, final int expected) throws IOException
   {
      try (BitReader input = new BitReader(new StringReader(hex)))
      {
         final int actual = input.readHex();
         assertEquals(expected, actual);
      }

   }
}
