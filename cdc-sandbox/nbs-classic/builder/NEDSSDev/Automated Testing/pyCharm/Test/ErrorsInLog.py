

file = open('C:\\Py\\DynamicDataMart.log', 'r')

file_lines = file.readlines()

for i in file_lines:
    if 'ERROR' in i:
        print(i)
file.close()