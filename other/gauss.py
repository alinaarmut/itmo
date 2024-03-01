import sys
import numpy as np
# с - множитель для обнуления элементов под диагональю в текущем столбце

def swap_lines(matrix, i, f):
    for j in range(i + 1, len(matrix)):
        if matrix[j][i] != 0:
            matrix[j], matrix[i] = matrix[i], matrix[j]
            f[j], f[i] = f[i], f[j]
            return [matrix, f]
    print("У матрицы нет уникальных решений!")
    # не будет уникальных значений если есть 0 на главной диагонали сразу писать из-за чего матрица не имеет уникальных значений
    sys.exit(0)


def print_matrix(matrix, f):
    for i in range(len(matrix)):
        for j in range(len(matrix)):
            print(round(matrix[i][j], 5), end=" ")
        print(round(f[i], 5))



def det_calc(matrix, k):
    print(k)
    det = -1 ** k
    for i in range(len(matrix)):
        det *= matrix[i][i]
    return det



# решает матрицу методом гаусса
def gauss_method_solution(matrix_list):

    matrix = matrix_list[0]
    print(np.linalg.det(matrix))
    n = len(matrix)
    f = matrix_list[1]
    swaps = 0
    for i in range(n - 1):
        if matrix[i][i] == 0:
            [matrix, f] = swap_lines(matrix, i, f)
            swaps += 1
        for k in range(i+1, n):
            c = matrix[k][i]/matrix[i][i]

            matrix[k][i] = 0
            for j in range(i+1, n):
                matrix[k][j] = matrix[k][j] - c * matrix[i][j]
            f[k] = f[k] - c * f[i]

           # print("Итерация ", i + 1)
           # print("Текущая матрица:")
           # print_matrix(matrix, f)

        print("Вывод треугольной матрицы:")
        print_matrix(matrix, f)
    x = [0] * n
    for i in range(n - 1, -1, -1):
        s = 0
        for j in range(i + 1, n):
            s = s + matrix[i][j] * x[j]
        if matrix[i][i] == 0:
            print("У матрицы нет уникальных решений!")
            sys.exit(0)
        x[i] = (f[i] - s) / matrix[i][i]
    print("Определитель матрицы: ", det_calc(matrix, swaps))

    return x