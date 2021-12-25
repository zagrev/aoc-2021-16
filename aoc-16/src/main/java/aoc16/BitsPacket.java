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
   /** the packet version */
   private int version = -1;
   /** the packet type */
   private int type = -1;
   /** the literal value if this packet is type 4 */
   private int literal = 0;

   /** any contained packets for all the other packet types */
   ArrayList<BitsPacket> subPackets = new ArrayList<>();

   /**
    * @return the literal
    */
   public int getLiteral()
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
    * @return the version
    */
   public int getVersion()
   {
      return version;
   }

   /**
    * @param literal
    *           the literal to set
    */
   public void setLiteral(final int literal)
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
}
