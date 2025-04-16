import pandas as pd
import matplotlib.pyplot as plt

# Read the CSV file
df = pd.read_csv("sensor_data.csv")

# Display the first few rows to check that the data is loaded
print(df.head())

# Assuming your table has columns "temperature" and "timestamp"
# Convert the timestamp column to datetime
df['timestamp'] = pd.to_datetime(df['timestamp'])

# Create a line plot of temperature over time
plt.figure(figsize=(10, 5))
plt.plot(df['timestamp'], df['temperature'], marker='o', linestyle='-')
plt.title("Sensor Temperature Over Time")
plt.xlabel("Timestamp")
plt.ylabel("Temperature")
plt.grid(True)
plt.tight_layout()
plt.show()
