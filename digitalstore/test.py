import csv

file_path = "C:\\Users\\Maique\\Desktop\\Codes\\MasterBank\\digitalstore\\data\\usernames.csv"
usernames = []
with open(file_path, "r") as f:
    f_contents = f.readlines()
    for line in f_contents:
        line = line.replace('"', '')
        line_content = line.split(',')
        if line_content[0] not in usernames:
            usernames.append(line_content[0])

print(usernames)
