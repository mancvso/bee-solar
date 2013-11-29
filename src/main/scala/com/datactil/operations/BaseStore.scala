package com.datactil.operations

trait BaseStore[T] {

	def add(arg: T):Unit

	def delete(arg: Option[Int]):Either[Unit, T]

	def update(arg: Option[Int]):Either[Unit, T]

	def list():Seq[T]
	
}