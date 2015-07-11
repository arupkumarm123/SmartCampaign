db.runCommand( { enableSharding : "test" } );
sh.shardCollection( "test.campaign", { id: "hashed" } );
