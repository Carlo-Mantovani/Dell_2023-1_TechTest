# Dell_2023-1_TechTest


  big = (int) (totalWeight / bigCapacity);
medium = (int) ((totalWeight % bigCapacity) / mediumCapacity);
        small = (int) ((totalWeight % bigCapacity) % mediumCapacity / smallCapacity);

Ex: totalWeight = 15000
big = (int) 15000/10000 = 1
medium = (int) (150000%10000) / 4000 = 1
small = (int) ((150000%10000) / 4000)/1000 = 1


Ex: totalWeight = 9999
big = (int) 9999/10000 = 0
medium = (int) (9999%10000) / 4000 = 2
small = (int) ((9999%10000) / 4000)/1000 = 2 (+1) = 3
Custo por km = (11,92*2) + (4,87*3) = 38,45
Custo por km para grande porte = 27,44

1;4.87
2;11.92
3;27.44

3 Transportes de Médio Porte = 12t de capacidade, custo: 35,76/km
2 Transportes de Pequeno Porte + 1 Transporte de Grande Porte
= 12t capacidade, custo: 

3 Transportes de Pequeno Porte = 3t capacidade, Custo: 14,61
1 Transporte de Medio Porte = 4t capacidade, Custo: 11,92



