package lectures.part3fp

import scala.annotation.tailrec

object S31TuplesAndMaps extends App {
  // tuples 1 to 22
  val tuple2a = new Tuple2[Int, String](1, "One")
  val tuple2b = Tuple2(2, "two")
  val tuple2c = (3, "Three") // Sugar for Tuple2(2, "two") or new Tuple2[Int, String](1, "One")
  println(tuple2a._1 + "-" +  tuple2a._2)  // 1-One
  println(tuple2a.copy(_2 = "Uno"))  // (1,Uno)
  println(tuple2a.swap)  // (One,1)

  // Map collection
  val aMap: Map[String, Int] = Map()  // empty Map
  val phoneBook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)  // key -> value is sugar for (key, value)
  println(phoneBook)
  println(phoneBook.contains("Jim")) // true
  println(phoneBook("Jim"))  // 555
  println(phoneBook("Mary"))  // -1
  println(phoneBook.get("Jim")) // Some(555)
  println(phoneBook.getOrElse("Mary", -2))  // -2
  println(phoneBook + ("Sue" -> 333)) // Map(Jim -> 555, Daniel -> 789, Sue -> 333) // Note the () around Tuple2
  println(phoneBook - "Daniel") // Map(Jim -> 555)
  // map, flatMap, filter
  println(phoneBook.map(pair => pair._1 + "-" + pair._2))  // List(Jim-555, Daniel-789)
  println(phoneBook.map{ case (k, v) => k + "-" + v})  // List(Jim-555, Daniel-789)  // Need {} around as case
  println(phoneBook.mapValues(v => v * 10)) // Map(Jim -> 5550, Daniel -> 7890)
  println(phoneBook.filterKeys(k => k.startsWith("J")))  // Map(Jim -> 555)
  // groupBy
  println(List("Bob", "James", "Angela", "Mary", "Daniel", "Jim").groupBy(x => x.charAt(0)))
    // Map(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))

  // Exercises
  val phoneBook2 = Map(("Jim", 555), "Daniel" -> 789, "JIM" -> 900)
  println(phoneBook2)
  println(phoneBook2.map(pair => pair._1.toLowerCase -> pair._2))  // List(Jim-555, Daniel-789)

/*   case class SocialNetwork() {
     private val network = Map[String, List[String]]()
     def add(name: String): SocialNetwork = {
       this
     }
  }*/


  type SocialNetwork = Map[String, Set[String]]
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }
  def addFriend(network: SocialNetwork, personA: String, personB: String): SocialNetwork = {
    val friendsAdded = for {
      friendsA <- network.get(personA)
      friendsB <- network.get(personB)
    } yield network + (personA -> (friendsA + personB)) + (personB -> (friendsB + personA))
    friendsAdded.getOrElse(network)
  }
  def unFriend(network: SocialNetwork, personA: String, personB: String): SocialNetwork = {
    val friendsAdded = for {
      friendsA <- network.get(personA)
      friendsB <- network.get(personB)
    } yield network + (personA -> (friendsA - personB)) + (personB -> (friendsB - personA))
    friendsAdded.getOrElse(network)
  }

  def removeLoop(network: SocialNetwork, person: String): SocialNetwork = {
    (network - person).map {  // unoptimised as going through everyone
      case (k, v) => k -> (v - person)
    }
  }

  def remove(network: SocialNetwork, person: String): SocialNetwork = {
    val unfriendAll = network.get(person).map { fs =>  // for each friend remove his/she from either friends list
      fs.map (f => f -> (network(f) - person))
    }
    (network - person) ++ unfriendAll.getOrElse(Set())
  }

  def removeRec (network: SocialNetwork, person: String): SocialNetwork = {
    @tailrec
    def removeAux(friends: Set[String], networkAcc: SocialNetwork): SocialNetwork = {
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unFriend(network, person, friends.head))
    }
    val unfriended = removeAux(network(person), network)
    unfriended - person
  }
  def nFriends(network: SocialNetwork, person: String): Int = {
    if (network.contains(person)) network(person).size else 0
  }

  def hasMostFiends(network: SocialNetwork): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  def numberWithNoFriends(network: SocialNetwork): Int = {
    //network.filter(pair => pair._2.isEmpty).size
    network.count(_._2.isEmpty)
  }

  def socialConnection1(network: SocialNetwork, source: String, target: String): Boolean = {
    @tailrec
    def isConnected (consideredPeople: Set[String], discoveredPeople: Set[String] ) : Boolean = {
      if (discoveredPeople.isEmpty) false        // no more people to check
      else {
        val person = discoveredPeople.head        // lets check the 1st person to be discovered
        if (person == target) true                // do they match the target
        else if (consideredPeople.contains(person))            // if already seen him before,
          isConnected(consideredPeople,discoveredPeople.tail)  //  skip and check next set of people
        else   // if not already seen before
               // add person to consider list and add his friends to the discovery list
          isConnected(consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }
    isConnected(Set(), network(source) + source)
  }

  def socialConnection(network: SocialNetwork, source: String, target: String): Boolean = {
    @tailrec
    def isConnected (consideredPeople: Set[String], discoveredPeople: Set[String] ) : Boolean = {
      if (discoveredPeople.isEmpty) false        // no more people to check
      else {
        val person = discoveredPeople.head        // lets check the 1st person to be discovered
        if (person == target) true                // do they match the target
//        else if (consideredPeople.contains(person))            // if already seen him before,
//          isConnected(consideredPeople,discoveredPeople.tail)  //  skip and check next set of people
        else   // if not already seen before
          isConnected(consideredPeople + person, // add person to consider list and
            discoveredPeople.tail ++ (network(person) -- consideredPeople)) // add his friends to the discovery list
                                                                            // who haven't already been considered
      }
    }
    isConnected(Set(), network(source) + source)
  }

  println()
  var sNetwork = Map[String, Set[String]]()
  sNetwork = add(sNetwork, "Peter")
  sNetwork = add(sNetwork, "Sue")
  sNetwork = add(sNetwork, "Jim")
  sNetwork = add(sNetwork, "Jane")
  println(sNetwork)
  sNetwork = addFriend(sNetwork, "Peter", "Jim")
  println(sNetwork)
  sNetwork = addFriend(sNetwork, "PeterX", "Jim")
  sNetwork = addFriend(sNetwork, "PeterX", "JimX")
  println(sNetwork)
  sNetwork = addFriend(sNetwork, "Peter", "Sue")
  println(sNetwork)
  sNetwork = unFriend(sNetwork, "Peter", "Jim")
  println(sNetwork)
  sNetwork = remove(sNetwork, "Jim")
  println(sNetwork)
  sNetwork = remove(sNetwork, "Allan")
  println(sNetwork)
  sNetwork = remove(sNetwork, "Peter")
  println(sNetwork)

  println()
  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(addFriend(network, "Bob", "Mary"))
  println(unFriend(addFriend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(addFriend(network, "Bob", "Mary"), "Bob"))

  println()
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = addFriend(people, "Bob", "Jim")
  val testNet = addFriend(jimBob, "Bob", "Mary")
  println(testNet)
  println(nFriends(testNet, "Bob"))
  println(hasMostFiends(testNet))
  println(numberWithNoFriends(testNet))
  println(numberWithNoFriends(network))
  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(testNet, "Mary", "Mary"))
  println(socialConnection(testNet, "Mary", "Lee"))
  println(socialConnection(network, "Mary", "Bob"))
















}
