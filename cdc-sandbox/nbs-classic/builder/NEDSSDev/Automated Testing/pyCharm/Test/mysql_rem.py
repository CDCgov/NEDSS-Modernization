import pyodbc


driver = 'SQL Server'
server = '10.62.0.128\\test1'
db = 'nbs_odse'
user = 'sa'
password = 'Nedss123!'

conn = pyodbc.connect('driver={%s};server=%s;database=%s;uid=%s;pwd=%s' % (driver, server, db, user, password))

cursor = conn.cursor()

cursor.execute('SELECT * FROM nbs_odse..Public_health_case where public_health_case_uid = 10011022')


print("cursor: ",cursor)
columns = [column[0] for column in cursor.description]
print (columns)

results = []
for row in cursor.fetchall():
    results.append(dict(zip(columns, row)))

print (results)

cursor1 = conn.cursor()
cursor1.execute('SELECT * FROM nbs_odse..Public_health_case where public_health_case_uid = 10011022')
for i in cursor1 :
  print(i)