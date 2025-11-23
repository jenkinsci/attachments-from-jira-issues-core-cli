def target = "<absolutePath>"

def letters = "abcdefghi"

def ran = new Random(42)

def totalCreated = 0

1000.times{ i ->
    def depth = 0 + ran.nextInt(8)
    def folders = (0..depth).collect{ letters[ran.nextInt(letters.size())] }
    def foldersPath = folders.join("/")
    def fullPath = target + "/" + foldersPath
    new File(fullPath).mkdirs()
    
    def numFiles = 1 + ran.nextInt(7)
    totalCreated += numFiles
    def files = (0..numFiles).collect{ fi ->
        "${i}-${fi}.txt"
    }
    
    println "${i} => ${numFiles} files to be created in ${foldersPath}"
    
    files.each{ f ->
        new File(fullPath, f) << (100 + ran.nextInt(899)).toString()
    }
}

println "======================="
println "Total: ${totalCreated} files created"
