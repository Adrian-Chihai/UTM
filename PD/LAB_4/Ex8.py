print("Ex 8")
def mean3Grades(gradesList):
    while len(gradesList) < 3:
        gradesList.append(4)

    return sum(gradesList) / 3

grades = {
"s1": [10, 10, 9],
"s2": [5, 6],
"s3": [8, 6, 9]
}

for student, sGrades in grades.items():
    print(f"Mean for {student}: {mean3Grades(sGrades)}")
