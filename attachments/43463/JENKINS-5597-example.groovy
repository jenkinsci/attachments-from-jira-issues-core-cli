
// Run on a node with the "first-node" label.
node() {
    // Make the output directory.

    sh( script: "/bin/rm -rf output first-stash" )

    sh "mkdir -p output"

    // Write a text file there.
    // writeFile file: "output/somefile", text: "Hey look, some text."

    sh( script: "mkdir -p output/test.Framework/Versions/A/Headers")
    writeFile file: "output/test.Framework/Versions/A/Headers/test.h", text: "// this is our test.h header file."
    sh( script: "mkdir output/test.Framework/Versions/A/Resources")
    writeFile file: "output/test.Framework/Versions/A/Resources/test.text", text: "This is our test Resources text file."
    writeFile file: "output/test.Framework/Versions/A/test", text: "echo This is our test executable."
    sh( script: "chmod 0755 output/test.Framework/Versions/A/test")
    sh( script: "cd output/test.Framework/Versions && ln -s A Current")
    sh( script: "cd output/test.Framework && ln -s Versions/Current/Headers .")
    sh( script: "cd output/test.Framework && ln -s Versions/Current/Resources .")
    sh( script: "cd output/test.Framework && ln -s Versions/Current/test .")

    // Stash that directory and file.
    // Note that the includes could be "output/", "output/*" as below, or even
    // "output/**/*" - it all works out basically the same.
    stash name: "first-stash", includes: "**"



    // Run the unstash from within that directory!
    dir("first-stash") {
        unstash "first-stash"
    }

    // Look, no output directory under the root!
    // pwd() outputs the current directory Pipeline is running in.
    sh "ls -Fla ${pwd()}/output/test.Framework"

    // And look, output directory is there under first-stash!
    sh "ls -Fla ${pwd()}/first-stash/output/test.Framework"
}