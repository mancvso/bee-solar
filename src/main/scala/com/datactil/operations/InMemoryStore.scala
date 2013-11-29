package com.datactl.operations

import scala.collection.mutable.HashMap

trait InMemoryStore[T] {
	val storage = HashMap.empty[Int, T]
}