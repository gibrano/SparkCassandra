import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.DataFrame
import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd._
import org.apache.spark.sql.cassandra._
import org.apache.log4j.{Level, Logger}

object SparkCassandra {
  def main(args: Array[String]) {
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
    val conf = new SparkConf(true).setAppName("Spark Cassandra Application").set("spark.cassandra.connection.host", "172.17.0.4")
    val sc = new SparkContext(conf)
    val selectStmt = args(0).split("\\,")
    val k = args(2).toInt
    var rdd = sc.cassandraTable("data", "events").select(selectStmt.map(ColumnName(_)):_*)

    if(args(1) != ""){
      val whereStmt = args(1).split("\\,")
      for(i <- 1 to whereStmt.size){
        var w = whereStmt(i-1).split("\\:")
        rdd = rdd.where(w(0),w(1))
      }
    }

    val output = rdd.keyBy(row => (row.getString("id"))).values
    System.out.println(output.take(k).mkString(", "))
    sc.stop()
  }
}
