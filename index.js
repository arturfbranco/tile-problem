var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
var currentValue = 1;
var SubMatrix = {
    Q1: "Q1",
    Q2: "Q2",
    Q3: "Q3",
    Q4: "Q4"
};
function Coordinates(n) {
    return {
        innerCorners: {
            upLeft: [n / 2 - 1, n / 2 - 1],
            upRight: [n / 2 - 1, n / 2],
            bottomLeft: [n / 2, n / 2 - 1],
            bottomRight: [n / 2, n / 2]
        },
        outerCorners: {
            upLeft: [0, 0],
            upRight: [0, n - 1],
            bottomLeft: [n - 1, 0],
            bottomRight: [n - 1, n - 1]
        }
    };
}
function processRealMissing(n, missing) {
    var realMissing = __spreadArray([], missing, true);
    while (realMissing[0] >= n) {
        realMissing[0] = realMissing[0] - n;
    }
    while (realMissing[1] >= n) {
        realMissing[1] = realMissing[1] - n;
    }
    return realMissing;
}
function mergeMatrixes(q1, q2, q3, q4) {
    var upperMatrix = [];
    for (var i = 0; i < q1.length; i++) {
        upperMatrix[i] = __spreadArray(__spreadArray([], q1[i], true), q2[i], true);
    }
    var bottomMatrix = [];
    for (var i = 0; i < q3.length; i++) {
        bottomMatrix[i] = __spreadArray(__spreadArray([], q3[i], true), q4[i], true);
    }
    return __spreadArray(__spreadArray([], upperMatrix, true), bottomMatrix, true);
}
function getMissingPositions(n, missI, missJ) {
    var coordinates = Coordinates(n);
    var result = {
        q1: coordinates.innerCorners.upLeft,
        q2: coordinates.innerCorners.upRight,
        q3: coordinates.innerCorners.bottomLeft,
        q4: coordinates.innerCorners.bottomRight
    };
    if (missI < n / 2) {
        if (missJ < n / 2) {
            delete result.q1;
        }
        else {
            delete result.q2;
        }
    }
    else {
        if (missJ < n / 2) {
            delete result.q3;
        }
        else {
            delete result.q4;
        }
    }
    return result;
}
function getSubMatrix(matrix, quarter) {
    var coordinates = Coordinates(matrix.length);
    switch (quarter) {
        case SubMatrix.Q1:
            return buildSubMatrix(matrix, [coordinates.outerCorners.upLeft, coordinates.innerCorners.upLeft]);
        case SubMatrix.Q2:
            var startQ2 = [0, coordinates.innerCorners.upRight[1]];
            var endQ2 = [coordinates.innerCorners.upRight[0], coordinates.outerCorners.upRight[1]];
            return buildSubMatrix(matrix, [startQ2, endQ2]);
        case SubMatrix.Q3:
            var startQ3 = [coordinates.innerCorners.bottomLeft[0], 0];
            var endQ3 = [coordinates.outerCorners.bottomLeft[0], coordinates.innerCorners.bottomLeft[1]];
            return buildSubMatrix(matrix, [startQ3, endQ3]);
        case SubMatrix.Q4:
            return buildSubMatrix(matrix, [coordinates.innerCorners.bottomRight, coordinates.outerCorners.bottomRight]);
        default:
            throw new Error();
    }
}
function buildSubMatrix(matrix, coordinates) {
    var subMatrix = [];
    var iSubMatrix = 0;
    var jSubMatrix = 0;
    var iStart = coordinates[0][0];
    var jStart = coordinates[0][1];
    var iEnd = coordinates[1][0];
    var jEnd = coordinates[1][1];
    for (var i = iStart; i <= iEnd; i++) {
        subMatrix[iSubMatrix] = [];
        jSubMatrix = 0;
        for (var j = jStart; j <= jEnd; j++) {
            subMatrix[iSubMatrix][jSubMatrix] = matrix[i][j];
            jSubMatrix++;
        }
        iSubMatrix++;
    }
    return subMatrix;
}
function fillBaseCase(missI, missJ) {
    var matrix = [[], []];
    matrix[0][0] = !(missI === 0 && missJ === 0) ? currentValue : -1;
    matrix[0][1] = !(missI === 0 && missJ === 1) ? currentValue : -1;
    matrix[1][0] = !(missI === 1 && missJ === 0) ? currentValue : -1;
    matrix[1][1] = !(missI === 1 && missJ === 1) ? currentValue : -1;
    currentValue++;
    return matrix;
}
function fill(matrix, missing) {
    var n = matrix.length;
    var realMissing = processRealMissing(n, missing);
    if (n === 2) {
        return fillBaseCase(realMissing[0], realMissing[1]);
    }
    var newMissing = getMissingPositions(n, realMissing[0], realMissing[1]);
    var q1 = fill(getSubMatrix(matrix, SubMatrix.Q1), newMissing.q1 ? newMissing.q1 : realMissing);
    var q2 = fill(getSubMatrix(matrix, SubMatrix.Q2), newMissing.q2 ? newMissing.q2 : realMissing);
    var q3 = fill(getSubMatrix(matrix, SubMatrix.Q3), newMissing.q3 ? newMissing.q3 : realMissing);
    var q4 = fill(getSubMatrix(matrix, SubMatrix.Q4), newMissing.q4 ? newMissing.q4 : realMissing);
    var filledMatrix = mergeMatrixes(q1, q2, q3, q4);
    if (newMissing.q1) {
        filledMatrix[newMissing.q1[0]][newMissing.q1[1]] = currentValue;
    }
    if (newMissing.q2) {
        filledMatrix[newMissing.q2[0]][newMissing.q2[1]] = currentValue;
    }
    if (newMissing.q3) {
        filledMatrix[newMissing.q3[0]][newMissing.q3[1]] = currentValue;
    }
    if (newMissing.q4) {
        filledMatrix[newMissing.q4[0]][newMissing.q4[1]] = currentValue;
    }
    currentValue++;
    return filledMatrix;
}
function solveTileProblem(n, missingI, missingJ) {
    if (missingI >= n || missingJ >= n) {
        throw new Error("Missing tile out of bounds.");
    }
    var matrix = [];
    currentValue = 1;
    for (var i = 0; i < n; i++) {
        matrix[i] = [];
        for (var j = 0; j < n; j++) {
            matrix[i][j] = 0;
        }
    }
    var solvedMatrix = fill(matrix, [missingI, missingJ]);
    currentValue = 1;
    return solvedMatrix;
}
var solvedValue = solveTileProblem(64, 10, 10);
console.log(solvedValue);
