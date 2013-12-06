package datactil.beesolar.security

object SHA1 {
	def encode(str:String):String = {
		val md = java.security.MessageDigest.getInstance("SHA-1")
		md.digest(str.getBytes).map(_ & 0xFF).map(_.toHexString).mkString
	}
}