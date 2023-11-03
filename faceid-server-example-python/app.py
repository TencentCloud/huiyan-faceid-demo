from flask import Flask
from ekyc.ekyc import ekyc_bp
from livenesscompare.liveness_compare import lc_bp

app = Flask(__name__)
app.register_blueprint(ekyc_bp)
app.register_blueprint(lc_bp)

if __name__ == '__main__':
    # listening port
    app.run(host='0.0.0.0', port=8080)
