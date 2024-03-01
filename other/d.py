# для вычисления вектора невязок в линейной системе уравнений
def get_discrepancies(matrix, x, f):

    dis = []
    for i in range(len(matrix)):
        s = 0
        for j in range(len(matrix)):
            s += matrix[i][j] * x[j]
        dis.append(f[i] - s)
    return dis