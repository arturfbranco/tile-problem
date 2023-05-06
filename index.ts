let currentValue = 1;

const SubMatrix = {
    Q1: "Q1",
    Q2: "Q2",
    Q3: "Q3",
    Q4: "Q4"
}

function Coordinates(n: number){
    return{
        innerCorners: {
            upLeft: [n/2 - 1, n/2 - 1],
            upRight: [n/2 - 1, n/2],
            bottomLeft: [n/2, n/2 - 1],
            bottomRight: [n/2, n/2]
        },
        outerCorners: {
            upLeft: [0, 0],
            upRight: [0, n-1],
            bottomLeft: [n-1, 0],
            bottomRight: [n-1, n-1]
        }
    }

}

function processRealMissing(n: number, missing: number[]): number[] {
    const realMissing = [...missing];
    while(realMissing[0] >= n){
        realMissing[0] = realMissing[0] - n
    }
    while(realMissing[1] >= n){
        realMissing[1] = realMissing[1] - n;
    }
    return realMissing;
}

function mergeMatrixes(q1: number[][], q2: number[][], q3: number[][], q4: number[][]): number[][] {
    const upperMatrix: number[][] = [];
    for(let i = 0; i < q1.length; i++){
        upperMatrix[i] = [...q1[i], ...q2[i]];
    }
    const bottomMatrix: number[][] = [];
    for(let i = 0; i < q3.length; i++){
        bottomMatrix[i] = [...q3[i], ...q4[i]];
    }
    return [...upperMatrix, ...bottomMatrix];
}

function getMissingPositions(n: number, missI: number, missJ: number) {
    const coordinates = Coordinates(n);
    const result = {
        q1: coordinates.innerCorners.upLeft,
        q2: coordinates.innerCorners.upRight,
        q3: coordinates.innerCorners.bottomLeft,
        q4: coordinates.innerCorners.bottomRight
    } as Partial<{q1: number[], q2: number[], q3: number[], q4: number[]}>
    if(missI < n / 2){

        if(missJ < n / 2){
            delete result.q1;
        } else {
            delete result.q2;
        }
    } else {

        if(missJ < n / 2){
            delete result.q3
        } else {
            delete result.q4;
        }
    }
    return result;

}

function getSubMatrix(matrix: number[][], quarter: string): number[][]{
    const coordinates = Coordinates(matrix.length);
    switch(quarter){
        case SubMatrix.Q1:
            return buildSubMatrix(matrix, [coordinates.outerCorners.upLeft, coordinates.innerCorners.upLeft]);
        case SubMatrix.Q2:
            const startQ2 = [0, coordinates.innerCorners.upRight[1]];
            const endQ2 = [coordinates.innerCorners.upRight[0], coordinates.outerCorners.upRight[1]]; 
            return buildSubMatrix(matrix, [startQ2, endQ2]);
        case SubMatrix.Q3:
            const startQ3 = [coordinates.innerCorners.bottomLeft[0], 0];
            const endQ3 = [coordinates.outerCorners.bottomLeft[0], coordinates.innerCorners.bottomLeft[1]];
            return buildSubMatrix(matrix, [startQ3, endQ3]);
        case SubMatrix.Q4:
            return buildSubMatrix(matrix, [coordinates.innerCorners.bottomRight, coordinates.outerCorners.bottomRight]);
        default:
            throw new Error();                   
    }
}

function buildSubMatrix(matrix: number[][], coordinates: number[][]): number[][]{
    const subMatrix: number[][] = [];
    let iSubMatrix = 0;
    let jSubMatrix = 0;
    const iStart = coordinates[0][0];
    const jStart = coordinates[0][1];
    const iEnd = coordinates[1][0];
    const jEnd = coordinates[1][1];
    for(let i = iStart; i <= iEnd; i++){
        subMatrix[iSubMatrix] = [];
        jSubMatrix = 0;
        for(let j = jStart; j <= jEnd; j++){
            subMatrix[iSubMatrix][jSubMatrix] = matrix[i][j];
            jSubMatrix++;
        }
        iSubMatrix++;
    }
    return subMatrix;
}

function fillBaseCase(missI: number, missJ: number): number[][] {
    const matrix: number[][] = [[],[]];
    matrix[0][0] = !(missI === 0 && missJ === 0) ? currentValue : -1;
    matrix[0][1] = !(missI === 0 && missJ === 1) ? currentValue : -1;
    matrix[1][0] = !(missI === 1 && missJ === 0) ? currentValue : -1;
    matrix[1][1] = !(missI === 1 && missJ === 1) ? currentValue : -1;
    currentValue++;
    return matrix;
}

function fill(matrix: number[][], missing: number[]):number[][]{
    const n = matrix.length;
    const realMissing = processRealMissing(n, missing);
    if(n === 2){
        return fillBaseCase(realMissing[0], realMissing[1]);
    }
    const newMissing = getMissingPositions(n, realMissing[0], realMissing[1]);
    const q1 = fill(getSubMatrix(matrix, SubMatrix.Q1), newMissing.q1 ? newMissing.q1 : realMissing);
    const q2 = fill(getSubMatrix(matrix, SubMatrix.Q2), newMissing.q2 ? newMissing.q2 : realMissing);
    const q3 = fill(getSubMatrix(matrix, SubMatrix.Q3), newMissing.q3 ? newMissing.q3 : realMissing);
    const q4 = fill(getSubMatrix(matrix, SubMatrix.Q4), newMissing.q4 ? newMissing.q4 : realMissing);
    
    const filledMatrix = mergeMatrixes(q1, q2, q3, q4);

    if(newMissing.q1){
        filledMatrix[newMissing.q1[0]][newMissing.q1[1]] = currentValue;
    }
    if(newMissing.q2){
        filledMatrix[newMissing.q2[0]][newMissing.q2[1]] = currentValue;
    }
    if(newMissing.q3){
        filledMatrix[newMissing.q3[0]][newMissing.q3[1]] = currentValue;
    }
    if(newMissing.q4){
        filledMatrix[newMissing.q4[0]][newMissing.q4[1]] = currentValue;
    }
    currentValue++;
    

    return filledMatrix;

}


function solveTileProblem(n: number, missingI: number, missingJ: number): number[][] {
    if(missingI >= n || missingJ >= n){
        throw new Error("Missing tile out of bounds.");
    }
    const matrix: number[][] = [];
    currentValue = 1;
    for(let i = 0; i < n; i++){
        matrix[i] = [];
        for(let j = 0; j < n; j++){
            matrix[i][j] = 0;
        }
    }
    const solvedMatrix = fill(matrix, [missingI, missingJ]);
    currentValue = 1;
    return solvedMatrix;
}

const solvedValue = solveTileProblem(64, 10, 10)
console.log(solvedValue)