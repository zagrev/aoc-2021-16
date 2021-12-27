/**
 *
 */
package aoc16;

import java.util.ArrayList;

/**
 *
 */
public class BitsPacket
{

   public final static int SUM = 0;
   public final static int PRODUCT = 1;
   public final static int MIN = 2;
   public final static int MAX = 3;
   public final static int LITERAL = 4;
   public final static int GT = 5;
   public final static int LT = 6;
   public final static int EQUAL = 7;

   /** the packet version */
   private int version = -1;
   /** the packet type */
   private int type = -1;
   /** the literal value if this packet is type 4 */
   private long literal = 0;
   /** the number of bits this packet represents */
   private int bitlength = 0;

   /** any contained packets for all the other packet types */
   ArrayList<BitsPacket> subPackets = new ArrayList<>();

   /**
    *
    */
   public BitsPacket()
   {
      // do nothing
   }

   /**
    * Creates a packet with the given version and type value
    *
    * @param version
    * @param type
    */
   public BitsPacket(final int version, final int type)
   {
      this.version = version;
      this.type = type;
   }

   /**
    * Creates a literal packet with the given version and literal value
    *
    * @param version
    * @param literal
    * @param bitlength
    */
   public BitsPacket(final int version, final long literal, final int bitlength)
   {
      this.version = version;
      this.literal = literal;
      this.bitlength = bitlength;

      this.type = 4; // literal
   }

   /**
    * @return the bitlength
    */
   public int getBitlength()
   {
      return bitlength;
   }

   /**
    * @return the literal
    */
   public long getLiteral()
   {
      return literal;
   }

   /**
    * @return the type
    */
   public int getType()
   {
      return type;
   }

   /**
    * @return the value of the packet
    */
   public long getValue()
   {
      final StringBuilder b = new StringBuilder();
      final long v = getValue(b);
      System.out.println(b.toString());
      return v;
   }

   /**
    * @param b
    * @return the value represented by this packet hierarchy
    */
   private long getValue(final StringBuilder b)
   {
      long value = 0;
      long v2;
      boolean firstTime = true;

      b.append("(");
      switch (getType())
      {
      case LITERAL:
         value = literal;
         b.append(value);
         break;

      case PRODUCT:
         value = 1;
         for (final BitsPacket p : subPackets)
         {
            if (firstTime)
            {
               firstTime = false;
            }
            else
            {
               b.append("*");
            }
            v2 = p.getValue(b);
            value *= v2;
         }
         break;

      case SUM:
         for (final BitsPacket p : subPackets)
         {
            if (firstTime)
            {
               firstTime = false;
            }
            else
            {
               b.append("+");
            }
            v2 = p.getValue(b);
            value += v2;
         }
         break;

      case MIN:
         value = Long.MAX_VALUE;
         b.append("min");
         for (final BitsPacket p : subPackets)
         {
            v2 = p.getValue(b);
            if (v2 < value)
            {
               value = v2;
            }
            b.append(",");
         }
         // kill the last comma
         b.setLength(b.length() - 1);
         break;

      case MAX:
         value = Long.MIN_VALUE;
         b.append("max");
         for (final BitsPacket p : subPackets)
         {
            v2 = p.getValue(b);
            if (v2 > value)
            {
               value = v2;
            }
            b.append(",");
         }
         // kill the last comma
         b.setLength(b.length() - 1);
         break;

      case GT:
         value = subPackets.get(0).getValue(b);
         b.append(">");
         v2 = subPackets.get(1).getValue(b);
         value = value > v2 ? 1 : 0;
         break;

      case LT:
         value = subPackets.get(0).getValue(b);
         b.append("<");
         v2 = subPackets.get(1).getValue(b);
         value = value < v2 ? 1 : 0;
         break;

      case EQUAL:
         value = subPackets.get(0).getValue(b);
         b.append("==");
         v2 = subPackets.get(1).getValue(b);
         value = value == v2 ? 1 : 0;
         break;

      default:
         throw new IllegalArgumentException("Invalid type: " + getType());
      }

      b.append(")");

      return value;
   }

   /**
    * @return the version
    */
   public int getVersion()
   {
      return version;
   }

   /**
    * @param bitlength
    *           the bitlength to set
    */
   public void setBitlength(final int bitlength)
   {
      this.bitlength = bitlength;
   }

   /**
    * @param literal
    *           the literal to set
    */
   public void setLiteral(final long literal)
   {
      this.literal = literal;
   }

   /**
    * @param type
    *           the type to set
    */
   public void setType(final int type)
   {
      this.type = type;
   }

   /**
    * @param version
    *           the version to set
    */
   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public String toString()
   {
      return "BitsPacket {version=" + version + ", type=" + PacketType.values()[type] + ", literal=" + literal
            + ", bitlength=" + bitlength + ", subPackets=" + subPackets + "}";
   }
}
