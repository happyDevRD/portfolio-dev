-- Contextualiza el banco como institución internacional sin revelar nombre (NDA).
UPDATE experiences
SET company = 'Banco Internacional (contrato, conf.)'
WHERE company = 'Institución Financiera (Remoto)';
