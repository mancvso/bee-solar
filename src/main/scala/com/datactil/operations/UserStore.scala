package com.datactil.operations
import com.datactil.models.User

object UserStore extends BaseStore[User] {
	def add(arg: com.datactil.models.User): Unit = ???
	def delete(arg: Option[Int]): Either[Unit,com.datactil.models.User] = ???
	def list(): Seq[com.datactil.models.User] = ???
	def update(arg: Option[Int]): Either[Unit,com.datactil.models.User] = ???
}