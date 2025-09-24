# git-project-andrew-jo

initializeRepo:
Call initializeRepo() to create the basic infrastructure for a git project. It'll create a git directory and populate it with an objects directory, index file, and HEAD file if those directories/files don't exist. Otherwise, it'll leave them as is. If the initialization adds anything, it'll let you know the repo has been successfully created. If you started with all of that infrastructure, it'll let you know the repo already exists.

hashFile:
The hashFile function takes in a file path and returns the hexstring of the contents of the file hashed by SHA-1. It usues two helper functions, extractContent, which tries to open the file and get the content in the file. Will throw a FileNotFoundException if the file path doesn't exist. Then, hashContent will take the content and return the hash string in hex, using helper functions getSHA to do the hashing and toHexString to turn it to hex.