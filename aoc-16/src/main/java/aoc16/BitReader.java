/**
 *
 */
package aoc16;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

/**
 * read a file of hex values as a bit stream;
 */
public class BitReader extends Reader
{
   /** the source of the bit data */
   private final Reader input;
   /** the internal bit buffer (64 bits) */
   private int bitBuffer;
   /** the number of valid bits will in the buffer */
   private int bitsInBuffer = 0;

   /**
    * creates a new bit stream using the input stream as a data source
    *
    * @param dataSource
    */
   public BitReader(final Reader dataSource)
   {
      input = dataSource;
   }

   @Override
   public void close() throws IOException
   {
      // TODO Auto-generated method stub

   }

   /**
    * @param hex
    * @return the binary value of the given hex pair
    */
   public int convertHex(final int hex)
   {
      int value = 0;

      switch (hex)
      {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
         value = hex - '0';
         break;
      case 'A':
      case 'B':
      case 'C':
      case 'D':
      case 'E':
      case 'F':
         value = hex - 'A' + 10;
         break;
      default:
         throw new IllegalArgumentException("Invalid hex input: " + hex);
      }

      return value;
   }

   /**
    * fill the buffer (up to 7 bytes). we save that last byte because we might have some bits left in the buffer
    *
    * @throws IOException
    */
   public void fillBuffer(final int length) throws IOException
   {
      while (bitsInBuffer < length)
      {
         // read the next byte (1 hex value or 4 bits)
         final int hex = input.read();

         if (hex == -1 && bitsInBuffer < length)
         {
            throw new EOFException();
         }

         // now put the bytes into the buffer
         bitBuffer = bitBuffer << 4 | convertHex(hex);

         // and update the available bit count
         bitsInBuffer += 4;
      }
   }

   @Override
   public int read(final char[] cbuf, final int off, final int len) throws IOException
   {
      int i;
      for (i = off; i < off + len; i++)
      {
         cbuf[i] = (char) (readBits(8) & 0x0ff);
      }
      return i;
   }

   /**
    * return the number of bits requested as an int. The length must be <= 32.
    *
    * @param length
    * @return an integer containing the number of requested bits in the least significant bits
    * @throws IOException
    */
   public int readBits(final int length) throws IOException
   {
      int value = 0;

      // if we don't have enough bits buffered, read bytes until we have enough bits
      while (bitsInBuffer < length)
      {
         fillBuffer(length);
      }

      final int shiftDistance = bitsInBuffer - length;
      value = bitBuffer >> shiftDistance;
      value = value & ~(0xffffffff << length);
      bitsInBuffer -= length;

      return value;
   }

   /**
    * read a single hex character. This reads 4 bits and converts the hex character into a binary value
    *
    * @return the binary nibble just read
    * @throws IOException
    */
   public int readHex() throws IOException
   {
      return convertHex(readBits(8));
   }
}
