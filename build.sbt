name := "Spark Cassandra Project"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.1.0"

val scalapbVersion = "0.6.0-pre4"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" %  "2.0.1" withSources() withJavadoc()
libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"
libraryDependencies += "com.stratio.receiver" % "spark-rabbitmq" % "0.5.1"
