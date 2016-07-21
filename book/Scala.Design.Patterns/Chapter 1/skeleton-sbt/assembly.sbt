assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) => {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case "unwanted.txt"     => MergeStrategy.discard
    case x => old(x)
  }
}


assemblyJarName in assembly := { s"${name.value}_${scalaVersion.value}-${version.value}-assembly.jar" }

artifact in (Compile, assembly) ~= {art =>
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)
