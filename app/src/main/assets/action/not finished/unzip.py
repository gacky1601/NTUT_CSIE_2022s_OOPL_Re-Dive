import zipfile
import os
folders=[]
NeedExtractFiles=['attack.zip', 'die.zip', 'idle.zip', 'run.zip', 'skill0.zip', 'skill1.zip', 'skill2.zip']
for i in os.listdir( "./" ):
    if "." not in i:
        folders.append(i)
for subfolders in folders:
    for ExtractingFiles in NeedExtractFiles:
        with zipfile.ZipFile('./'+subfolders+"/"+ExtractingFiles) as z:
            for fileinzip in z.namelist():
                if fileinzip !="images/":
                    try:
                        os.mkdir("./"+subfolders+"/"+ExtractingFiles[:-4])
                    except:
                        pass
                    with open("./"+subfolders+"/"+ExtractingFiles[:-4]+"/"+fileinzip[7:], 'wb') as f:
                        f.write(z.read(fileinzip))
        os.remove('./'+subfolders+"/"+ExtractingFiles)
print(folders)