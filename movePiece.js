function getPiece(square) {
    const id = square.id;
    const piesa = square.getAttribute('data-piesa');
    getInfoCasa(id, piesa);
}

function getInfoCasa(id, piesa) {
    alert(`Casa ${id} contine piesa: ${piesa}`);
}