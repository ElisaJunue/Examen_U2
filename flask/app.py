from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Habilita CORS para todas las rutas y orígenes

@app.route('/contacto', methods=['POST'])
def contacto():
    data = request.get_json()  # Obtener JSON enviado desde el cliente
    print("Datos recibidos en /contacto:", data)
    return jsonify({"mensaje": "Datos recibidos correctamente", "datos": data})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
