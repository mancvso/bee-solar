package com.datactil.models

import sprest.util.enum.{EnumCompanion, Enum}

/* Tipo de dispositivo conectado a Wifi */
sealed abstract class Device(val id: String, val label: String, val value: Int) extends Enum[Device](id)
object Device extends EnumCompanion[Device] {
  case object Other extends Device("other", "Otro", 0)
  case object Mobile extends Device("mobile", "Móvil", 1)
  case object Laptop extends Device("laptop", "Computador", 2)
  case object Tablet extends Device("tablet", "Tablet", 3)
  case object Console extends Device("console", "Consola", 4)

  // This is necessary for Device.all to return all Enums due to
  // Scala objects being lazily class loaded
  register( Other, Mobile, Laptop, Tablet, Console )
}


/* Nivel de Severidad del Alerta */
sealed abstract class Severity(val id: String, val label: String, val value: Int) extends Enum[Severity](id)
object Severity extends EnumCompanion[Severity] {
  case object Low extends Severity("low", "Low", 0)
  case object Normal extends Severity("normal", "Normal", 1)
  case object High extends Severity("high", "Alto", 2)

  // This is necessary for Severity.all to return all Enums due to
  // Scala objects being lazily class loaded
  register( Low, Normal, High )
}

