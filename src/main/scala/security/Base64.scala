package datactil.beesolar.security

object Base64 {

  def encode(str: String) : String = {
      new sun.misc.BASE64Encoder().encode( str.getBytes )
  }

}
    